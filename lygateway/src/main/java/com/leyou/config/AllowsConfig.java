package com.leyou.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @date 2019/1/19-20:42
 */
@ConfigurationProperties
@Data
public class AllowsConfig {
	private List<String> allowPaths;
}
