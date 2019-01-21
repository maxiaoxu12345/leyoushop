package com.leyou.api;

import com.leyou.pojo.Sku;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @date 2019/1/11-17:33
 */
public interface SkuApi {
	@GetMapping("sku/list")
	public List<Sku> querySkusByid(@RequestParam("id") Long id);
	@GetMapping("sku/list/{ids}")
	public List<Sku> querySkusByids(@PathVariable("ids") List<Long> ids);
}
