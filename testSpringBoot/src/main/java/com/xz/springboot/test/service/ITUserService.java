package com.xz.springboot.test.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xz.springboot.test.model.TUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2018-07-24
 */
public interface ITUserService extends IService<TUser> {

	List<TUser> getList();

}
