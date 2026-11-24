package org.kenny.spring.entity;

import java.util.Properties;

public class DataSource2 {
    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "DataSource2{" +
                "properties=" + properties +
                '}';
    }
}
