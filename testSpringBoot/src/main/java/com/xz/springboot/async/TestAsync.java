package com.xz.springboot.async;

import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xz.springboot.Application;
import com.xz.springboot.async.service.AsyncService;
import com.xz.springboot.async.service.FutureService;

//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestAsync {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestAsync.class);
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private FutureService futureService;

	@Test
	public void testAsync() throws Exception {
		asyncService.AsyncA();
		asyncService.AsyncB();
		LOGGER.info("complete");
		Thread.sleep(100000);
	}

	@Test
	public void testFuture() throws Exception {
		Future<String> futureA = futureService.futureA();
		Future<String> futureB = futureService.futureB();
		while (true) {
			if (futureA.isDone() && futureB.isDone()) {
				LOGGER.info("complete");
				break;
			}
		}
	}
}
