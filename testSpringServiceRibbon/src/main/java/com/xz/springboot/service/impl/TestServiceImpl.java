package com.xz.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xz.springboot.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "testError")
	@Override
	public String testService(String name) {
		return restTemplate.getForObject("http://SERVICE-CLIENT/hi?name=" + name, String.class);
	}

	public String testError(String name) {
		return "hi," + name + ",sorry,error!";
	}

}
