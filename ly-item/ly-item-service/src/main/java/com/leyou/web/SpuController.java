package com.leyou.web;

import com.leyou.common.vo.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2019/1/8-16:12
 */
@RestController
@RequestMapping("spu")
public class SpuController {
	@Autowired
	private SpuService spuService;
	@GetMapping("page")
	public ResponseEntity<PageResult<Spu>> querySpuByPage(
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "saleable",defaultValue = "true") Boolean saleable,
			@RequestParam(value = "rows", defaultValue = "5") Integer rows,
			@RequestParam(value = "page", defaultValue = "1") Integer page
	) {

		return ResponseEntity.ok(spuService.querySpuByPage(key,saleable,rows,page));
	}
@GetMapping("detail/{id}")
	public ResponseEntity<SpuDetail> querySpuDetailByid(@PathVariable("id") Long id){
		return ResponseEntity.ok(spuService.querySpuDetailByid(id));
}
	@GetMapping("{id}")
	public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
		Spu spu = spuService.querySpuById(id);

		return ResponseEntity.ok(spu);
	}
}
