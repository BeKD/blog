package com.chen.service;

import com.chen.entity.User;

/**
 * Created by limi on 2017/10/15.
 */
public interface UserService {

    //    核对用户名和密码
    User checkUser(String username, String password);
}
