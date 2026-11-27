package org.kenny.spring.bean;

public class Client {
    private String name;

    public Client() {
        System.out.println("1.实例化Bean");
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("2.Bean属性赋值");
    }

    public void initBean(){
        System.out.println("3.初始化Bean");
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                '}';
    }

    public void destroyBean(){
        System.out.println("5.销毁Bean");
    }

}
