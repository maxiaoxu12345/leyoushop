package com.leyou.config;

import com.leyou.auth.utils.RsaUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @date 2019/1/19-18:21
 */
@Data
@ConfigurationProperties
public class CookieConfig {
	private String pubKeyPath;// 公钥

	private String cookieName;
	private PublicKey publicKey; // 公钥
	@PostConstruct
	public void init() {
		try {
			this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
		} catch (Exception e) {
		throw new LyException(ExceptionEnum.USER_EXPLAIN_ERROR);
		}
	}
}
