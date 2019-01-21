package com.leyou.web;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @date 2018/12/28-8:14
 */
@RestController
@RequestMapping("category")
public class CategoryController {
	@Autowired
private 	CategoryService categoryService;
	@GetMapping("list")
	public ResponseEntity<List<Category>> getListByPid(@RequestParam Long pid) {
		List<Category> clist = categoryService.getListByPid(pid);
		return ResponseEntity.ok(clist);

	}
	@GetMapping("list/ids")
	public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
		List<Category> listByPids = categoryService.getListByPids(ids);
		return ResponseEntity.ok(listByPids);
	}
	@GetMapping("listAll")
	public ResponseEntity<List<String>> queryCategoryAllName(){
		List<String> names = categoryService.queryCategoryAllName();
		return ResponseEntity.ok(names);
	}
}
