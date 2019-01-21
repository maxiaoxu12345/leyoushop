package com.leyou.cart.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @date 2019/1/20-3:36
 */
@Data
@Slf4j
@ConfigurationProperties
public class TokenConfig {
	private String pubKeyPath;
	private String cookieName;
	private PublicKey publicKey; // 公钥
	@PostConstruct
	public void init()  {
		try {
			this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
		} catch (Exception e) {
			log.error("公钥解析失败，原因{}", e);
		}
	}

}
