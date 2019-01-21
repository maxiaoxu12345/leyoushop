package com.leyou.service;

import com.leyou.client.*;
import com.leyou.pojo.*;
import com.leyou.utils.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2019/1/14-10:28
 */
@Slf4j
@Service
public class PageService {
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
	@Autowired
	TemplateEngine templateEngine;
	public Map<String,Object> loadModels(Long pid) {
		Map<String,Object> map=new HashMap<>();
		Spu spu = goodsClient.querySpuById(pid);
		List<Sku> skus = spu.getSkus();
		SpuDetail detail = spu.getSpuDetail();
		List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
		Brand brand = brandClient.queryBrandById(spu.getBrandId());
		List<Group> specs = specClient.getGroups(spu.getCid3());
		map.put("spu", spu);
		map.put("subTitle",spu.getSubTitle() );
		map.put("skus", skus);
		map.put("detail", detail);
		map.put("brand", brand);
		map.put("specs", specs);
		return map;
	}
	public void creatHtml(Long pid){
		Context context=new Context();
			context.setVariables(loadModels(pid));
		File file=new File("C:\\nginx-1.8.0\\nginx-1.8.0\\html\\item",pid+".html");
		if (file.exists()){
			file.delete();
		}
		PrintWriter pw=null;
		try  {
			pw = new PrintWriter(file,"UTF-8");
				templateEngine.process("item",context ,pw );

		}catch (Exception e){
			log.error("打印页面错误",e );
		}finally {
			if (pw!=null){
			pw.close();
		}
		}

	};
	/**
	 * 新建线程处理页面静态化
	 * @param spuId
	 */

	public void asyncExecute(Long spuId) {
		ThreadUtil.execute(() ->creatHtml(spuId));
	}

}
