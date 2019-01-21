package com.leyou.order.config;

import com.leyou.order.interceptor.UserInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @date 2019/1/20-3:46
 */
@Configuration
@EnableConfigurationProperties(TokenConfig.class)
public class InterceptorConfig implements WebMvcConfigurer {
@Autowired
	private TokenConfig tokenConfig;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserInfoInterceptor(tokenConfig)).addPathPatterns("/**").excludePathPatterns("/notify/pay");
	}
}
