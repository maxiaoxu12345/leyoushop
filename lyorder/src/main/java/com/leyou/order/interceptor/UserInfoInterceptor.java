package com.leyou.order.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.order.config.TokenConfig;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @date 2019/1/20-3:33
 */

public class UserInfoInterceptor implements HandlerInterceptor {

   private TokenConfig config;
	private static final ThreadLocal<UserInfo> tl=new ThreadLocal<>();

	public UserInfoInterceptor(TokenConfig config) {
		this.config=config;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = CookieUtils.getCookieValue(request, config.getCookieName());
		UserInfo userInfo = JwtUtils.getInfoFromToken(token, config.getPublicKey());
		tl.set(userInfo);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		tl.remove();
	}
	public static UserInfo getUserInfo(){
		return tl.get();
	}
}
