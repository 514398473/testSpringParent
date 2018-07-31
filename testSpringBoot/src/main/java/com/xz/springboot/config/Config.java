package com.xz.springboot.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;

@Configuration
@MapperScan({"com.xz.springboot.*.mapper"})
@EnableAsync
public class Config {

	/***
	 * plus 的性能优化
	 * 
	 * @return
	 */
	@Bean
	public PerformanceInterceptor performanceInterceptor() {
		PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
		/* <!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 --> */
		performanceInterceptor.setMaxTime(1000);
		/* <!--SQL是否格式化 默认false--> */
		performanceInterceptor.setFormat(false);
		return performanceInterceptor;
	}

	/**
	 * @Description : mybatis-plus分页插件 ---------------------------------
	 * @Author : Liang.Guangqing
	 * @Date : Create in 2017/9/19 13:59
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

}
