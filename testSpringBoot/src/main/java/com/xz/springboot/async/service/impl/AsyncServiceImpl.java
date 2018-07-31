package com.xz.springboot.async.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.xz.springboot.async.service.AsyncService;

@Service
public class AsyncServiceImpl implements AsyncService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncServiceImpl.class);

	@Override
	@Async
	public void AsyncA() {
		LOGGER.info("start AsyncA");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("end AsyncA");
	}

	@Override
	@Async
	public void AsyncB() {
		LOGGER.info("start AsyncB");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("end AsyncB");
	}

}
