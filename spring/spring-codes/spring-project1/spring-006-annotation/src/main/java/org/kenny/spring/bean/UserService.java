package org.kenny.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service //此时bean的名称是userService
public class UserService {

//    @Autowired
//    private UserDao userDao;
//    @Autowired
//    public UserService(UserDao userDao) {
//        this.userDao = userDao;
//    }

    private IUserDao userDao;

//    @Autowired
//    public UserService(IUserDao userDao) {
//        this.userDao = userDao;
//    }

    @Autowired
    @Qualifier("userDaoMysql")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
    public void save() {
        userDao.insert();
    }

    @Override
    public String toString() {
        return "UserService{" +
                "userDao=" + userDao +
                '}';
    }
}
