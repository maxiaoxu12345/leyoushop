package com.leyou.service;

import com.leyou.mapper.SkuMapper;
import com.leyou.mapper.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2019/1/9-11:18
 */
@Service
public class SkuService {
	@Autowired
	SkuMapper skuMapper;
	@Autowired
	StockMapper stockMapper;
	private List<Long> ids=new ArrayList<>();
	public List<Sku> querySkusByid(Long id) {
		Sku sku=new Sku();
		sku.setSpuId(id);
		List<Sku> skus = skuMapper.select(sku);
		for (Sku skus1 : skus) {
			ids.add(skus1.getId());
		}
		List<Stock> stocks = stockMapper.selectByIdList(ids);
		for (Sku skus1 : skus) {
			for (Stock stock : stocks) {
				if (skus1.getId().equals(stock.getSkuId())){
					skus1.setStock(stock.getStock());
				}
			}

		}
		return skus;
	}

	public List<Sku> querySkusByids(List<Long> ids) {
		return  skuMapper.selectByIdList(ids);
	}
}
