package org.kenny.spring.entity;

public class FakeUser {
    private String name; //这个用来测试空值""
    private String email;//这个用来测试null

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FakeUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
