package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @date 2018/12/26-18:35
 */
@EnableEurekaServer
@SpringBootApplication
public class RegistryApplication {
	public static void main(String[] args) {
		SpringApplication.run(RegistryApplication.class);
	}
}
