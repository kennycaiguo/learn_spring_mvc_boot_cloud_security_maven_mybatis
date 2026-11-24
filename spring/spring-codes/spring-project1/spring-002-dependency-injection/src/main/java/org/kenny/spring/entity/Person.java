package org.kenny.spring.entity;

import java.util.Arrays;

public class Person {
    private String name;
    private String[] favoriteFoods;
    //这里不需要带参构造函数，因为我们使用set方法来设置
    public void setFavoriteFoods(String[] favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", favoriteFoods=" + Arrays.toString(favoriteFoods) +
                '}';
    }
}
