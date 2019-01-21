package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * @date 2018/12/27-12:57
 */
@Data
public class ExceptionResult {
	private int status;
	private String message;
	private Long timestamp;
	public ExceptionResult(ExceptionEnum exceptionEnum){
		this.status=exceptionEnum.getCode();
		this.message=exceptionEnum.getMessage();
		this.timestamp=System.currentTimeMillis();
	}

}
