package com.leyou.service;

import com.leyou.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @date 2018/12/27-8:30
 */
@Service
public class UserSevice {
	public User saveUser(User user){
		Integer id=new Random().nextInt();
		user.setId(id);
		return user;
	}
}
