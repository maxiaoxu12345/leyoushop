package com.leyou.order.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * @date 2019/1/21-16:24
 */
@Data
public class PayConfig implements WXPayConfig {
	private String appID;
	private String mchID;
	private String key;
	private int httpConnectTimeoutMs;
	private int httpReadTimeoutMs;
	private String notifyUrl;


	@Override
	public InputStream getCertStream() {
		return null;
	}
}
