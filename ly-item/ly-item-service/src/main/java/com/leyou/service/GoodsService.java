package com.leyou.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.mapper.SkuMapper;
import com.leyou.mapper.SpuDetailMapper;
import com.leyou.mapper.SpuMapper;
import com.leyou.mapper.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import org.apache.ibatis.annotations.Select;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.soap.Detail;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 2019/1/9-8:47
 */
@Service
public class GoodsService {
	@Autowired
	SpuMapper spuMapper;
	@Autowired
	SkuMapper skuMapper;
	@Autowired
	StockMapper stockMapper;
	@Autowired
	SpuDetailMapper spuDetailMapper;
	@Autowired
	AmqpTemplate amqpTemplate;
	private List<Stock> stocks=new ArrayList<>();
	@Transactional
	public void addSPUAndSpudetailAndSKUAndStock(Spu spu) {
		//添加SPU
		spu.setSaleable(true);
		spu.setValid(true);
		spu.setCreateTime(new Date());
		spu.setLastUpdateTime(new Date());
		int i2 = spuMapper.insert(spu);
		if (i2!=1){
			throw new LyException(ExceptionEnum.GOODS_INSERT_DEFAULTED);
		}
		//添加SPUdetail
		spu.getSpuDetail().setSpuId(spu.getId());
		int i1 = spuDetailMapper.insert(spu.getSpuDetail());
		if (i1!=1){
			throw new LyException(ExceptionEnum.GOODS_INSERT_DEFAULTED);
		}
		addSKUAndStrock(spu);
		//发送mq
		amqpTemplate.convertAndSend("item.insert",spu.getId());
	}


@Transactional
	public void editSPUAndSpudetailAndSKUAndStock(Spu spu) {
		Sku sku=new Sku();
		sku.setSpuId(spu.getId());
		List<Sku> skus=skuMapper.select(sku);
		if (!CollectionUtils.isEmpty(skus)){
			skuMapper.delete(sku);
			List<Long> ids =skus.stream().map(Sku::getId).collect(Collectors.toList());
			stockMapper.deleteByIdList(ids);
		}
		spu.setValid(null);
		spu.setSaleable(null);
		spu.setLastUpdateTime(new Date());
		spu.setCreateTime(null);
		int i = spuMapper.updateByPrimaryKeySelective(spu);
		if (i!=1){
			throw new LyException(ExceptionEnum.GOODS_UPDATE_DEFAULT);
		}
		int i1 = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
		if (i1!=1){
			throw new LyException(ExceptionEnum.GOODS_UPDATE_DEFAULT);
		}
		addSKUAndStrock(spu);
	//发送mq
	amqpTemplate.convertAndSend("item.update",spu.getId());
	}
	/*
	* 公用添加方法
	* */
	private void addSKUAndStrock(Spu spu) {
		//添加SKU
		for (Sku sku : spu.getSkus()) {
			sku.setSpuId(spu.getId());
			sku.setEnable(true);
			sku.setCreateTime(new Date());
			sku.setLastUpdateTime(new Date());
			int insert = skuMapper.insert(sku);
			if (insert!=1){
				throw new LyException(ExceptionEnum.GOODS_INSERT_DEFAULTED);
			}
			//添加stock到集合
			Long stock = sku.getStock();
			Stock stock1 = new Stock();
			stock1.setSkuId(sku.getId());
			stock1.setStock(stock);
			stocks.add(stock1);
		}
		//批量添加集合到库
		int insert=	stockMapper.insertList(stocks);

		if (insert!=spu.getSkus().size()){
			throw new LyException(ExceptionEnum.GOODS_INSERT_DEFAULTED);
		}
		stocks.clear();
	}
}
