package org.kenny.spring.bean;

public class User {

    public User() {
        System.out.println("无参构造函数被调用");
    }
    public User(String name) {
        System.out.println(name);
    }

}
