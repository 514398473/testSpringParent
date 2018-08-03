package com.xz.springboot.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestJson {

	@GetMapping("/testJsonException")
	public String testJsonException() {
		int a = 1 / 0;
		return "testJsonException";
	}
}
