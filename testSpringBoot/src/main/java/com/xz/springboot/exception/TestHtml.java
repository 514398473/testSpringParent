package com.xz.springboot.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestHtml {

	@RequestMapping("/testHtmlException")
	public String testHtmlException() {
		int a = 1 / 0;
		return "testHtmlException";
	}
}
