package com.xz.springboot.hystric;

import org.springframework.stereotype.Component;

import com.xz.springboot.service.TestFeignService;

@Component
public class TestHystric implements TestFeignService {

	@Override
	public String testFeign(String name) {
		return "sorry " + name;
	}

}
