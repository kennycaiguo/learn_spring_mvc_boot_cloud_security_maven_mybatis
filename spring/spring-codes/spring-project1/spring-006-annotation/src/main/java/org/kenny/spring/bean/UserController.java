package org.kenny.spring.bean;

import org.springframework.stereotype.Controller;

@Controller //此时bean的名称是userController
public class UserController {
    private UserService userService;
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
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
