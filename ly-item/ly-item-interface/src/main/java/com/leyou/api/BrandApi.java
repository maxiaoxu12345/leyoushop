package com.leyou.api;

import com.leyou.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @date 2019/1/11-15:27
 */
public interface BrandApi {
	@GetMapping("brand/{id}")
	public Brand queryBrandById(@PathVariable("id") Long id);
	@GetMapping("brand/ids")
	public List<Brand> queryBrandByIds(@RequestParam("brandIds") List<Long> ids);
}
