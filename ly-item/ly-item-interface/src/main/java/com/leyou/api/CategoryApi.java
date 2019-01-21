package com.leyou.api;

import com.leyou.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @date 2019/1/11-15:04
 */

public interface CategoryApi {
	@GetMapping("category/list/ids")
	public List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
	@GetMapping("category/listAll")
	public List<String> queryCategoryAllName();

}
