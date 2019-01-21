package com.leyou.order.config;

import com.leyou.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2019/1/21-10:19
 */
@Configuration
@EnableConfigurationProperties(IdConfig.class)
public class EnableIdConfig {
	@Bean
	public IdWorker idWorker(IdConfig idConfig){
		return new IdWorker(idConfig.getWorkId(),idConfig.getDatacenterId());
	}
}
