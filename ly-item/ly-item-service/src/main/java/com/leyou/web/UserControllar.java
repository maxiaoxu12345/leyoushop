package com.leyou.web;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.pojo.User;
import com.leyou.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/12/27-8:34
 */
@RestController
@RequestMapping("item")
public class UserControllar {
	@Autowired
	UserSevice userSevice;
	@GetMapping
	public ResponseEntity<User> saveUser(User user){
		User newUser= userSevice.saveUser(user);
			if (null==newUser.getName()){
				throw  new LyException(ExceptionEnum.NAME_CONNOT_BE_NOT);
			}
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}

}
