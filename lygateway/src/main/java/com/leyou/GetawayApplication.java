package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @date 2018/12/26-19:22
 */
@EnableZuulProxy
@SpringCloudApplication
public class GetawayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GetawayApplication.class);
	}

}
