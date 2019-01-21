package com.leyou.upload.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @date 2019/1/7-16:49
 */
@Data
@ConfigurationProperties(prefix = "ly.upload")
public class UploadProperties {
	private String baseUrl;
	private List<String> allowTypes;
}
