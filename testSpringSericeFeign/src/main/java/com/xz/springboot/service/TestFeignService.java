package com.xz.springboot.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xz.springboot.hystric.TestHystric;

@FeignClient(value = "service-client",fallback = TestHystric.class)
public interface TestFeignService {

	@RequestMapping(value = "hi", method = RequestMethod.GET)
	String testFeign(@RequestParam(value = "name") String name);
}
