package org.kenny.spring.dao.impl;

import org.kenny.spring.dao.UserDao;

public class UserDaoImpl implements UserDao {
    @Override
    public void insert() {
        System.out.println("保存用户信息");
    }
}
