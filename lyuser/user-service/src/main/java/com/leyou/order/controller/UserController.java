package com.leyou.order.controller;

import com.leyou.order.pojo.User;
import com.leyou.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser
            (@PathVariable("data") String data,@PathVariable("type") Integer type){
    	return ResponseEntity.ok(userService.checkUser(data,type));
	}
	@PostMapping("register")
	public ResponseEntity<Void> register(@Valid User user,@RequestParam("code") String code){
		userService.register(user);
    	return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	@GetMapping("query")
	public ResponseEntity<User> query(@RequestParam("username") String username,@RequestParam("password") String password){

		return ResponseEntity.ok(userService.query(username,password));
	}
}