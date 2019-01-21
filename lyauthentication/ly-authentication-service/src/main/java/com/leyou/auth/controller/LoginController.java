package com.leyou.auth.controller;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.service.LoginService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @date 2019/1/18-12:58
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private JwtProperties jwtProperties;

	@PostMapping("accredit")
	public ResponseEntity<Void> login(@RequestParam("username")String username,
									  @RequestParam("password") String password, HttpServletResponse response, HttpServletRequest request){
		String token=loginService.login(username,password);
		CookieUtils.newBuilder(response).httpOnly().request(request).build
				(jwtProperties.getCookieName(), token);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	@GetMapping("verify")
	public ResponseEntity<UserInfo> verity(@CookieValue("lyCookie") String token,HttpServletResponse response, HttpServletRequest request){
		try {
			//验证token
			UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
			//刷新token
			String newToken = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
			CookieUtils.newBuilder(response).httpOnly().request(request).build
					(jwtProperties.getCookieName(), newToken);
			return ResponseEntity.ok(userInfo);
		} catch (Exception e) {
			throw new LyException(ExceptionEnum.USER_EXPLAIN_ERROR);
		}

	}

}
