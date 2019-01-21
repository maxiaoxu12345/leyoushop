package com.leyou.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @date 2019/1/21-10:16
 */
@Data
@ConfigurationProperties
public class IdConfig {
	private Long workId;
	private Long datacenterId;

}
