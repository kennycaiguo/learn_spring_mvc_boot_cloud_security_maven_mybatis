package org.kenny.spring.bean4;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {
    //@Value("Jackline")
    private String name;
//    @Value("30")
    private Integer age;

    public User(@Value("Mary") String name, @Value("19") Integer age) {
        this.name = name;
        this.age = age;
    }

    //    @Value("Becky")
    public void setName(String name) {
        System.out.println("setName Called..." );
        this.name = name;
    }

//    @Value("28")
    public void setAge(Integer age) {
        System.out.println("setAge Called..." );
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
