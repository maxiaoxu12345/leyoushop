package com.leyou;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @date 2019/1/13-21:23
 */
@Controller
public class HelloController {
		@GetMapping("msg")
	public String test(Model model){
		model.addAttribute("msg","Hello,thymeleaf!");
		return "hello";
	
	}
}
