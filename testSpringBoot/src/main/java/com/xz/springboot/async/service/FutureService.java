package com.xz.springboot.async.service;

import java.util.concurrent.Future;

public interface FutureService {

	Future<String> futureA();

	Future<String> futureB();
}
