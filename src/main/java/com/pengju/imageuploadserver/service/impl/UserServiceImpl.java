package com.pengju.imageuploadserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pengju.imageuploadserver.bean.User;
import com.pengju.imageuploadserver.mapper.UserMapper;
import com.pengju.imageuploadserver.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author pengju
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User getByAccount(String account) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",account);
        return getOne(queryWrapper);
    }
}
