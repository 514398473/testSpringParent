package com.xz.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xz.springboot.service.TestFeignService;

@RestController
public class TestFeignController {

	@Autowired
	private TestFeignService testFeignService;

	@GetMapping("/hi")
	public String hi(@RequestParam String name) {
		return testFeignService.testFeign(name);
	}
}
