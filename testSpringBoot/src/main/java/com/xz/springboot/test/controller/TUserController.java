package com.xz.springboot.test.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xz.springboot.test.model.TUser;
import com.xz.springboot.test.service.ITUserService;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author
 * @since 2018-07-24
 */
@RestController
@RequestMapping("/user")
public class TUserController {

	@Resource
	private ITUserService iUserService;

	@GetMapping("/getUser")
	@ResponseBody
	public Object getUser() {
		List<TUser> list = iUserService.getList();
		return list;
	}
}
