package com.leyou.web;

import com.leyou.pojo.Spu;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2019/1/9-8:42
 */
@RestController
@RequestMapping("goods")
public class GoodsController {
	@Autowired
	GoodsService goodsService;
	@GetMapping
	public ResponseEntity<Void> addSPUAndSpudetailAndSKUAndStock(@RequestBody Spu spu){
	goodsService.addSPUAndSpudetailAndSKUAndStock(spu);
		return ResponseEntity.ok().build();
	}
	@PutMapping
	public ResponseEntity<Void> editSPUAndSpudetailAndSKUAndStock(@RequestBody Spu spu){
		goodsService.editSPUAndSpudetailAndSKUAndStock(spu);
		return ResponseEntity.ok().build();
	}
}
