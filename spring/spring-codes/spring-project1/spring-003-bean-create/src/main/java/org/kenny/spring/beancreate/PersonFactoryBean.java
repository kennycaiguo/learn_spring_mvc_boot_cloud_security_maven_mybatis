package org.kenny.spring.beancreate;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

public class PersonFactoryBean implements FactoryBean<Person> {

    @Nullable
    @Override
    public Person getObject() throws Exception {
        return new Person();
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return null; //可以使用默认的
    }

    @Override
    public boolean isSingleton() {
        // true表示单例
        // false表示原型
        return true;
    }
}
