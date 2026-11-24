package org.kenny.spring.entity;

import java.util.Properties;

public class MyConf {
    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "MyConf{" +
                "properties=" + properties +
                '}';
    }
}
