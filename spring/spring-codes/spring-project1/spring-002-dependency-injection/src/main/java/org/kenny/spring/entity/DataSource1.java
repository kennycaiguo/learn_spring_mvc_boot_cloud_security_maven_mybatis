package org.kenny.spring.entity;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSource1 {
    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "DataSource1{" +
                "properties=" + properties +
                '}';
    }
}
