package org.kenny.spring.bean;

import lombok.RequiredArgsConstructor;
import org.kenny.spring.bean4.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyService {
    private final UserDao userDao;

//    public MyService(UserDao userDao) {
//        this.userDao = userDao;
//    }

    public void save() {
        userDao.insert();
    }
    @Override
    public String toString() {
        return "MyService{" +
                "userDao=" + userDao +
                '}';
    }
}
