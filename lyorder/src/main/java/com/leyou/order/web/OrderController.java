package com.leyou.order.web;

import com.leyou.order.service.OrderService;
import com.leyou.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @date 2019/1/21-9:41
 */
@RestController
public class OrderController {
	@Autowired
	OrderService orderService;
	@PostMapping("to")
	public ResponseEntity<Long> creatOrder(@RequestBody Order order, HttpServletRequest request){

		Long id = orderService.creatOrder(order);

		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}
	@GetMapping("url/{id}")
	public ResponseEntity<String> getPayURL(@PathVariable("id") Long id){
		System.out.println(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.getPayURL(id));
	}
}
