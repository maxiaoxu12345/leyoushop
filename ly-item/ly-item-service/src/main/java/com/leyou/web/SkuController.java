package com.leyou.web;

import com.leyou.pojo.Sku;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @date 2019/1/9-11:14
 */
@RequestMapping("sku")
@Controller
public class SkuController {
	@Autowired
	SkuService skuService;
	@GetMapping("list")
	public ResponseEntity<List<Sku>> querySkusByid(@RequestParam("id") Long id){
		ResponseEntity<List<Sku>> ok = ResponseEntity.ok(skuService.querySkusByid(id));
		return ok;
	}
	@GetMapping("list/{ids}")
	public ResponseEntity<List<Sku>> querySkusByids(@PathVariable("ids") List<Long> ids){
		ResponseEntity<List<Sku>> ok = ResponseEntity.ok(skuService.querySkusByids(ids));
		return ok;
	}
}
