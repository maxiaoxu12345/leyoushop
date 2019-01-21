package com.leyou.api;

import com.leyou.common.vo.PageResult;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @date 2019/1/11-17:30
 */
public interface SpuApi {

	@GetMapping("spu/page")
	public PageResult<Spu> querySpuByPage(
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "saleable",defaultValue = "true") Boolean saleable,
			@RequestParam(value = "rows", defaultValue = "5") Integer rows,
			@RequestParam(value = "page", defaultValue = "1") Integer page
	);
	@GetMapping("spu/detail/{id}")
	public SpuDetail querySpuDetailByid(@PathVariable("id") Long id);
	/**
	 * 根据spu的id查询spu
	 * @param id
	 * @return
	 */
	@GetMapping("spu/{id}")
	public Spu querySpuById(@PathVariable("id") Long id);
}
