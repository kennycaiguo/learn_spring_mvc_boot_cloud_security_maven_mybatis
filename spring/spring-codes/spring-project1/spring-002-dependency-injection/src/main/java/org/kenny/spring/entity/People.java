package org.kenny.spring.entity;

import java.util.List;
import java.util.Map;

public class People {
    private String name;
    // 一个人有多个名字
    private List<String> nicknames;
    //增加一个emails属性，一个人可以有多个email
    private Map<Integer,String> emails;

    public People() {}
    public void setEmails(Map<Integer, String> emails) {this.emails = emails;}

    public void setName(String name) {this.name = name;}

    public void setNicknames(List<String> nicknames) {this.nicknames = nicknames;}

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", nicknames=" + nicknames +
                ", emails=" + emails +
                '}';
    }
}

