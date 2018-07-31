package com.xz.springboot.test.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xz.springboot.test.mapper.TUserMapper;
import com.xz.springboot.test.model.TUser;
import com.xz.springboot.test.service.ITUserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2018-07-24
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

	@Override
	public List<TUser> getList() {
		return this.selectList(null);
	}

}
