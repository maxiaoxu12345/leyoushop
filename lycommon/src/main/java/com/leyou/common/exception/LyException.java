package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @date 2018/12/27-13:09
 */

public class LyException extends RuntimeException {
	private ExceptionEnum exceptionEnum;

	public LyException(ExceptionEnum exceptionEnum) {
		this.exceptionEnum = exceptionEnum;
	}

	public LyException() {
	}

	public ExceptionEnum getExceptionEnum() {
		return exceptionEnum;
	}
}
