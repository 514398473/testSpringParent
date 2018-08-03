package com.xz.springboot.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.xz.springboot.model.ErrorInfo;

@RestControllerAdvice
public class TestJsonException {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestJsonException.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ErrorInfo<String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		LOGGER.info("进入RestController异常处理");
		ErrorInfo<String> r = new ErrorInfo<>();
		r.setMessage(e.getMessage());
		r.setCode(ErrorInfo.ERROR);
		r.setData("Some Data");
		r.setUrl(req.getRequestURL().toString());
		return r;
	}
}
