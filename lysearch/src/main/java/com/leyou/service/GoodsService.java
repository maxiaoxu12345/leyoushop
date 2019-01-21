package com.leyou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.client.*;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.common.utils.NumberUtils;
import com.leyou.common.vo.PageResult;
import com.leyou.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @date 2019/1/11-16:26
 */
@Service
public class GoodsService {
	@Autowired
	SpuClient goodsClient;
	@Autowired
	BrandClient brandClient;
	@Autowired
	CategoryClient categoryClient;
	@Autowired
	SpecClient specClient;
	@Autowired
	SkuClient skuClient;

public Goods ChangeTo(Spu spu){
	//整理数据

	List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
		List<String> strings=new ArrayList<>();
	for (Category category : categories) {
		strings.add(category.getName());
	}
	if (CollectionUtils.isEmpty(strings)){
			throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
		}
	List<Sku> skus = skuClient.querySkusByid(spu.getId());
	if (CollectionUtils.isEmpty(skus)){
		throw new LyException(ExceptionEnum.SKU_NOT_FOUND);
	}
	Set<Long> prices=new HashSet<>();
	List<String> list=new ArrayList<>();
	List<Map<String,Object>> newSKU=new ArrayList<>();
	for (Sku sku : skus) {
		list.add(sku.getTitle());
		prices.add(sku.getPrice());
		Map<String,Object> map=new HashMap<>();
		map.put("id", sku.getId());
		map.put("title", sku.getTitle());
		map.put("price", sku.getPrice());
		map.put("image", StringUtils.substringBefore(sku.getImages(),"," ));
		newSKU.add(map);
	}
	String all=spu.getTitle()+
			brandClient.queryBrandById(spu.getBrandId()).getName()+ StringUtils.join(strings," " )+
			StringUtils.join(list, " ")
			;

	//规格参数处理
	List<Param> params = specClient.getParam(null, spu.getCid3(), true);
	if (CollectionUtils.isEmpty(params)){
		throw new LyException(ExceptionEnum.PARAM_IS_NOT_FIND);
	}
	SpuDetail spuDetails = goodsClient.querySpuDetailByid(spu.getId());
	String genericSpec = spuDetails.getGenericSpec();
	Map<String, String> map1 = JsonUtils.toMap(genericSpec, String.class, String.class);
	String specialSpec = spuDetails.getSpecialSpec();
	Map<String, List<String>> map2 = JsonUtils.nativeRead(specialSpec, new TypeReference<Map<String, List<String>>>() {
	});
	Map<String,	Object> map=new HashMap<>();
	Object 	value ="";
	for (Param param : params) {
		String key=param.getName();
		if (param.getGeneric()){
			value=map1.get(param.getId().toString());
			if (param.getNumeric()){
				value= chooseSegment(value.toString(), param);
			}
		}else {
			value=	map2.get(param.getId().toString());
		}
		map.put(key,value );
	}
	Goods goods=new Goods();
	goods.setAll(all);//用于搜索的字段包括标题，分类，品牌，规格等；
	goods.setPrice(prices);
	goods.setSkus(JsonUtils.toString(newSKU));
	goods.setSpecs(map);
	goods.setBrandId(spu.getBrandId());
	goods.setCid1(spu.getCid1());
	goods.setCid2(spu.getCid2());
	goods.setCid3(spu.getCid3());
	goods.setCreateTime(spu.getCreateTime());
	goods.setId(spu.getId());
	goods.setSubTitle(spu.getSubTitle());
	return goods;
}
	private String chooseSegment(String value, Param p) {
		double val = NumberUtils.toDouble(value);
		String result = "其它";
		// 保存数值段
		for (String segment : p.getSegments().split(",")) {
			String[] segs = segment.split("-");
			// 获取数值范围
			double begin = NumberUtils.toDouble(segs[0]);
			double end = Double.MAX_VALUE;
			if(segs.length == 2){
				end = NumberUtils.toDouble(segs[1]);
			}
			// 判断是否在范围内
			if(val >= begin && val < end){
				if(segs.length == 1){
					result = segs[0] + p.getUnit() + "以上";
				}else if(begin == 0){
					result = segs[1] + p.getUnit() + "以下";
				}else{
					result = segment + p.getUnit();
				}
				break;
			}
		}
		return result;
	}

}
