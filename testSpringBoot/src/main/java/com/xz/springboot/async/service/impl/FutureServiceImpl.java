package com.xz.springboot.async.service.impl;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.xz.springboot.async.service.FutureService;

@Service
public class FutureServiceImpl implements FutureService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FutureServiceImpl.class);

	@Override
	@Async
	public Future<String> futureA() {
		LOGGER.info("start FutureA");
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("end FutureA");
		return new AsyncResult<String>("success");
	}

	@Override
	@Async
	public Future<String> futureB() {
		LOGGER.info("start FutureB");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("end FutureB");
		return new AsyncResult<String>("success");
	}

}
