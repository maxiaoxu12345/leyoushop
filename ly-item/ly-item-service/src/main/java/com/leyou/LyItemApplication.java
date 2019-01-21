package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @date 2018/12/26-21:36
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.leyou.mapper")
public class LyItemApplication {
	public static void main(String[] args) {
		SpringApplication.run(LyItemApplication.class);
	}
}
