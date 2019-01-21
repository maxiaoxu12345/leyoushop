package com.leyou.cart.Controller;

import com.leyou.cart.Service.CartService;
import com.leyou.cart.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @date 2019/1/20-4:20
 */
@RestController
public class CartController {
	@Autowired
	private CartService cartService;
	@PostMapping
	public ResponseEntity<Void> addCart(@RequestBody Cart cart){
		cartService.addCart(cart);
		return ResponseEntity.status(HttpStatus.CREATED).build();

	}
	@GetMapping
	public ResponseEntity<List<Cart>> getCart(){

		return ResponseEntity.ok(cartService.getCart());

	}
	@PutMapping
	public ResponseEntity<Void> putCart(@RequestParam("skuId") Long skuId,@RequestParam("num") Integer num ){
		cartService.putCart(skuId,num);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
	@DeleteMapping("{skuId}")
	public ResponseEntity<Void> deleteCart(@PathVariable("skuId") Long skuId ){
		cartService.deleteCart(skuId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
}
