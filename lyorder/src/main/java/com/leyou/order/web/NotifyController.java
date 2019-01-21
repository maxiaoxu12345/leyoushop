package com.leyou.order.web;

import com.leyou.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2019/1/21-22:44
 */
@RestController
@RequestMapping
public class NotifyController {
	@Autowired
	OrderService orderService;
	@GetMapping(value = "notify/pay",produces ="application/xml" )
	@ResponseBody
	public Map<String,String> notify(@RequestBody Map<String,String> result, HttpServletRequest request){
		System.out.println(request.getServletPath());
		orderService.notify2(result);
		Map<String,String> msg=new HashMap<>();
		msg.put("return_code", "SUCCESS");
		msg.put("return_msg", "OK");
		return msg;

	}
	@GetMapping("state{id}")
	public Integer getState(@PathVariable("id") Long id){
		return orderService.getState(id);

	}
}
