package com.leyou.controller;

import com.leyou.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2019/1/14-9:15
 */
@Controller
@RequestMapping("item")
public class PageController {
	@Autowired
	private PageService pageService;
	@GetMapping("{id}.html")
	public String ToItemPage(@PathVariable("id") Long pid, Model model){
		Map<String,Object> attrs=pageService.loadModels(pid);
		model.addAllAttributes(attrs);
		pageService.creatHtml(pid);
		return "item";
	}
}
