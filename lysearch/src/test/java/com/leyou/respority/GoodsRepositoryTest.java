package com.leyou.respority;


import com.leyou.service.GoodsService;
import com.leyou.client.SpuClient;
import com.leyou.common.vo.PageResult;
import com.leyou.pojo.Goods;
import com.leyou.pojo.Spu;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 2019/1/11-15:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {
	//复杂查询用
	@Autowired
	ElasticsearchTemplate template;
	//做增删改查用
	@Autowired
	GoodsRepository repository;
	@Autowired
	SpuClient goodsClient;
	@Autowired
	GoodsService service;
	@org.junit.Test
	public void TestCreat(){
		//创建索引库
			template.createIndex(Goods.class);
		//创建映射
		template.putMapping(Goods.class);
	}
	@org.junit.Test
	public void LoadGoods(){
		int page=1;
		int rows=100;
		int size1=0;
		do {
			PageResult<Spu> result = goodsClient.querySpuByPage("1245678",true,rows,page);

		List<Spu> items = result.getItems();
		if (CollectionUtils.isEmpty(items)){
			break;
		};
		List<Goods> goods = items.stream().map(service::ChangeTo).collect(Collectors.toList());
		repository.saveAll(goods);
		page++;
		rows=items.size();
		}while(rows==100);
	}

}