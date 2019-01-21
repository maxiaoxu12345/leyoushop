package com.leyou.common.advice;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @date 2018/12/27-11:37
 */
@ControllerAdvice
public class ControllarAdvice {
	@ExceptionHandler(LyException.class)
	public ResponseEntity<ExceptionResult> RunTimeHandle(LyException lyException){
		ExceptionEnum exceptionEnum = lyException.getExceptionEnum();
		return ResponseEntity.status(exceptionEnum.getCode()).body(new ExceptionResult(exceptionEnum));

	}
}
