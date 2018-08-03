package com.xz.springboot;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.xz.springboot.interceptor.TestInterceptor;

@SpringBootConfiguration
@MapperScan({ "com.xz.springboot.*.mapper" })
@EnableAsync
public class Config extends WebMvcConfigurationSupport {

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

	/**
	 * 添加拦截器
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new TestInterceptor());
	}

	/**
	 * 将 JSON 引擎替换为 fastJSON
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		/*
		 * 1.需要先定义一个convert转换消息的对象； 2.添加fastjson的配置信息，比如是否要格式化返回的json数据
		 * 3.在convert中添加配置信息 4.将convert添加到converters中
		 */
		// 1.定义一个convert转换消息对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 2.添加fastjson的配置信息，比如：是否要格式化返回json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		converters.add(fastConverter);
	}
}
