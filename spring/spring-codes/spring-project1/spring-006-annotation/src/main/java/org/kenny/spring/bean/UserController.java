package org.kenny.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller //此时bean的名称是userController
public class UserController {
//    @Autowired
    private UserService userService;
//    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
    public void saveData() {
        userService.save();
    }

    @Override
    public String toString() {
        return "UserController{" +
                "userService=" + userService +
                '}';
    }
}
