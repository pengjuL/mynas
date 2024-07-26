package com.pengju.nasSystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pengju.nasSystem.bean.User;

/**
 * @author pengju
 */
public interface UserService extends IService<User> {

    /**
     * 根据账号查找用户
     * @param account 账号
     * @return 用户 或 空
     */
    User getByAccount(String account);
}
