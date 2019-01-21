package com.leyou.service;

import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;


import com.leyou.client.SpecClient;
import com.leyou.common.vo.PageResult;
import com.leyou.pojo.*;
import com.leyou.respority.GoodsRepository;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @date 2019/1/12-0:39
 */
@Service
public class SearchService {
	@Autowired
	GoodsRepository repository;
	@Autowired
	ElasticsearchTemplate template;
	@Autowired
	BrandClient brandClient;
	@Autowired
	CategoryClient categoryClient;
	@Autowired
	SpecClient specClient;
	public PageResult<Goods> search(SearchRequest request) {

		//创建原生查询器
		NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
		//创建查询条件
		QueryBuilder basicQuery =buildbasicQuery(request);

		builder.withQuery(basicQuery);
		//对返回的结果过滤
		builder.withSourceFilter(new FetchSourceFilter(new String[]{"id","skus","subTitle"},null));
		//聚和分类条件
		builder.addAggregation(AggregationBuilders.terms("categoryAggs").field("cid3"));
		//聚和品牌条件
		builder.addAggregation(AggregationBuilders.terms("brandAggs").field("brandId"));

		//分页(从0开始)
		builder.withPageable(PageRequest.of(request.getPage()-1,request.getSize() ));
		SearchQuery query = builder.build();
		AggregatedPage<Goods> goods = template.queryForPage(builder.build(), Goods.class);
		//解析分页结果
		//解析聚合结果
		Aggregations aggs = goods.getAggregations();
		List<Category> categories= parseCategoryagg(aggs.get("categoryAggs"));
		List<Brand> brands= parseBrandagg(aggs.get("brandAggs"));
		//当分类确定时,聚合规格
		List<Map<String,Object>> spec=null;
		if (categories!=null&&categories.size()==1){
			//根据分类id和当前查询结果确定需要聚合的规格值
			spec=buildSpecAgg(categories.get(0).getId(),basicQuery);
		}
		return new SearchResult(goods.getTotalElements(),goods.getTotalPages(),goods.getContent(),categories,
				brands,spec);

	}

	private QueryBuilder buildbasicQuery(SearchRequest request) {
		//匹配并过滤的查询
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		//查询条件
		boolQueryBuilder.must(QueryBuilders.matchQuery("all", request.getKey()));
		//创造过滤条件
		Map<String, String> filter = request.getFilter();

		if (!filter.containsKey("key")){

		Map<String, String> spec = request.getFilter();
		for (Map.Entry<String, String> entry : spec.entrySet()) {
			//过滤项中的key作为过滤条件
			String key = entry.getKey();
			if (!key.equals("cid3")&&!key.equals("brandId")){
				key="specs."+key+".keyword";
			}
			//value作为过滤值
			String value = entry.getValue();
			boolQueryBuilder.filter(QueryBuilders.termQuery(key,value ));
		}}
		return boolQueryBuilder;
	}

	private List<Map<String,Object>> buildSpecAgg(Long cid, QueryBuilder all) {
		List<Map<String,Object>> spec=new ArrayList<>();
		//确定需要聚合的规格参数
		List<Param> params = specClient.getParam(null, cid, true);
		//创建原生查询器
		NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
		//带上目前查询结果
		builder.withQuery(all);
		//聚合
		for (Param param : params) {
			String name = param.getName();
			builder.addAggregation(AggregationBuilders.terms(name).field("specs."+name+".keyword"));
		}
			//	获取聚合结果
		AggregatedPage<Goods> goods = template.queryForPage(builder.build(), Goods.class);
		//	解析聚合
		Aggregations aggregations = goods.getAggregations();
		for (Param param : params) {
			//准备规格参数名称
			String name = param.getName();
			StringTerms terms = aggregations.get(name);
			//准备带选项
			List<String> options = terms.getBuckets().stream().map(b -> b.getKeyAsString()).collect(Collectors.toList());
			Map<String,Object> map=new HashMap<>();
			map.put("k",name );
			map.put("options",options );
			spec.add(map);
		}
		return spec;
	}

	private List<Brand> parseBrandagg(LongTerms brandAggs) {
		try {
			List<Long> brandIds = brandAggs.getBuckets().stream().map(b -> b.getKeyAsNumber().longValue()).collect(Collectors.toList());

		return  brandClient.queryBrandByIds(brandIds);
		}catch (Exception e){
			return null;
		}
	}

	private List<Category> parseCategoryagg(LongTerms categoryAggs) {
		try {
			List<Long> categoryIds = categoryAggs.getBuckets().stream().map(b -> b.getKeyAsNumber().longValue()).collect(Collectors.toList());
			return 	categoryClient.queryCategoryByIds(categoryIds);
		}catch (Exception e){
			return null;
		}
	}
}
