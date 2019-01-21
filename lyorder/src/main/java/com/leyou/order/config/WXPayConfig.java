package com.leyou.order.config;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2019/1/21-16:38
 */
@Configuration
public class WXPayConfig {
	@Bean
	@ConfigurationProperties
	public PayConfig payConfig(){
		return new PayConfig();
	}
		@Bean
		public WXPay wxPay(PayConfig payConfig){
			return new WXPay(payConfig, WXPayConstants.SignType.HMACSHA256);
		}
	}




