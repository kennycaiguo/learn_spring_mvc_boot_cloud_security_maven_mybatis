package org.kenny.spring.entity;

public class MyMath {
    private String result;

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MyMath{" +
                "result='" + result + '\'' +
                '}';
    }
}
