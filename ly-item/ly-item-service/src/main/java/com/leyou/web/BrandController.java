package com.leyou.web;

import com.leyou.common.vo.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @date 2019/1/4-10:48
 */
@RestController
@RequestMapping("brand")
public class BrandController {
	@Autowired
	private BrandService brandService;
	@GetMapping("page")
	public ResponseEntity<PageResult<Brand>> getCurrBrand(@RequestParam Boolean desc,
														 @RequestParam String key,
														 @RequestParam Integer page,
														 @RequestParam Integer rows,
														 @RequestParam String sortBy
	) {

		return ResponseEntity.ok(brandService.getCurrBrand(desc,key,page,rows,sortBy));
	}
	@PostMapping
	public ResponseEntity<Void> addBrand(Brand brand,@RequestParam("cids") List<Long> cids){
		brandService.addBrand(brand,cids);
		return ResponseEntity.status(HttpStatus.CREATED).build();

	}
	@GetMapping("ids")
	public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("brandIds") List<Long> ids){
		return	 ResponseEntity.ok(brandService.queryBrandByIds(ids));
	};
	@GetMapping("cid/{id}")
	public ResponseEntity<List<Brand>> queryBrandListByCid(@PathVariable("id") Long cid){

		return ResponseEntity.ok(brandService.queryBrandListByCid(cid));

	}
	@GetMapping("{id}")
	public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
		return ResponseEntity.ok(brandService.queryById(id));
	}
}
