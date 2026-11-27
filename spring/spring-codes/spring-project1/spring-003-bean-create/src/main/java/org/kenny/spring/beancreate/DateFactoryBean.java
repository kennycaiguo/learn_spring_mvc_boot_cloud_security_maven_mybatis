package org.kenny.spring.beancreate;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFactoryBean implements FactoryBean<Date> {
    // 定义属性接收日期字符串
    private String date;
    // 通过构造方法给日期字符串属性赋值
    public DateFactoryBean(String date) {
        this.date = date;
    }
    @Nullable
    @Override
    public Date getObject() throws Exception {
        //格式化日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(this.date);
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
