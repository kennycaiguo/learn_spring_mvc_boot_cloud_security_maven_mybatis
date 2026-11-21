package org.kenny.spring.controller;

import org.kenny.spring.service.UserService;

public class UserController {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void saveUser() {
        System.out.println("UserController saveUser start....");
        userService.save();
        System.out.println("UserController saveUser end....");
    }
}
