package com.leyou;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @date 2019/1/4-22:18
 */
@EnableDiscoveryClient
@SpringBootApplication
public class  UploadApplication {
	public static void main(String[] args) {
		SpringApplication.run(UploadApplication.class);
	}
}
