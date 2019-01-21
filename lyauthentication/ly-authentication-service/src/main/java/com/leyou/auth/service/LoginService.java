package com.leyou.auth.service;

import com.leyou.auth.client.LoginClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.order.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @date 2019/1/18-13:11
 */
@Slf4j
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class LoginService {
			@Autowired
			JwtProperties properties;
		@Autowired
		LoginClient loginClient;
	public String login(String username, String password) {
		User user = loginClient.query(username, password);
		if (user==null){
			throw new LyException(ExceptionEnum.USERNAME_OR_PASSWORD_IS_ERROR);
		}
		try {
			String token = JwtUtils.generateToken(
					new UserInfo(user.getId(), username), properties.getPrivateKey(), properties.getExpire());
			return token;
		} catch (Exception e) {
			log.error("[生成token错误]，原因{}",e);
			throw new LyException(ExceptionEnum.USERNAME_OR_PASSWORD_IS_ERROR);
		}

	}
}
