package org.kenny.spring.service.impl;

import org.kenny.spring.service.UserService;
import org.kenny.spring.dao.UserDao;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private UserDao userDao2;

    //添加setter方法
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserDao2(UserDao userDao2) {
        this.userDao2 = userDao2;
    }

    @Override
    public void save() {
        System.out.println("UserService save begin....");
        userDao.insert();
        userDao2.insert();
        System.out.println("UserService save end....");
    }
}
