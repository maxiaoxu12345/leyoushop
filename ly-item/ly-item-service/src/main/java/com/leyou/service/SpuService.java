package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.mapper.SpuDetailMapper;
import com.leyou.pojo.Sku;
import com.leyou.service.SkuService;
import com.leyou.mapper.SpuMapper;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 2019/1/8-17:31
 */
@Service
public class SpuService {
	@Autowired
	SpuMapper spuMapper;
	@Autowired
	SpuDetailMapper spuDetailMapper;
	@Autowired
	CategoryService categoryService;
	@Autowired
	SkuService skuService;
	@Autowired
	BrandService brandService;
	@Transactional
	public PageResult<Spu> querySpuByPage(String key, Boolean saleable, Integer rows, Integer page) {
		//分页
		PageHelper.startPage(page,rows );
		//准备条件查询
		Example example = new Example(Spu.class);
		Example.Criteria criteria = example.createCriteria();
			//按搜索字段过滤
		if (!"1245678".equals(key) && StringUtils.isNotBlank(key)  ){
			System.out.println("11111111");
		criteria.andLike("title","%"+key+"%" );
		}
		//上下架过滤
		if (saleable!=null) {
			criteria.andEqualTo("saleable", saleable?1:0);
		}
		//默认排序
		example.setOrderByClause("last_update_time desc");

		//查询
		List<Spu> list = spuMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)){
			throw new LyException(ExceptionEnum.GOODS_IS_NOT_FOUND);
		}
		//根据页面需求将商品分类和品牌转换成对应的文字信息
		LoadCategoryAndBrand(list);

		PageInfo pageInfo =new PageInfo(list);
		return new PageResult<>(pageInfo.getTotal(),list);

	}

	private void LoadCategoryAndBrand(List<Spu> list) {
		for (Spu spu : list) {
			//处理商品分类
			List<String> names = categoryService.getListByPids(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
					.stream().map(c -> c.getName()).collect(Collectors.toList());
			spu.setCname(StringUtils.join(names,"/"));
			//处理品牌名称
			spu.setBname(brandService.queryById(spu.getBrandId()).getName());
		}

	}

	public SpuDetail querySpuDetailByid(Long id) {
		SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(id);
		return spuDetail;
	}

	public Spu querySpuById(Long id) {
		//查询Spu,SpuDetail,Skus
		Spu spu = this.spuMapper.selectByPrimaryKey(id);
		if (spu==null){
			throw new LyException(ExceptionEnum.SPU_NOT_FOUND);
		}
		List<Sku> skus = skuService.querySkusByid(id);
		spu.setSkus(skus);
		SpuDetail spuDetail = querySpuDetailByid(id);
		spu.setSpuDetail(spuDetail);
		return spu;
	}
}
