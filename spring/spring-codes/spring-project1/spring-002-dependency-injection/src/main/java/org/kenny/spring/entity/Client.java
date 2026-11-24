package org.kenny.spring.entity;

public class Client {
    private String name;
    private Integer age;


    public Client(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
