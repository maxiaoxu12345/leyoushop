package com.leyou.order.api;

import com.leyou.order.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * @date 2019/1/18-13:12
 */
public interface UserApi {
	@GetMapping("query")
	User query(@RequestParam("username") String username,
			   @RequestParam("password") String password);
}
