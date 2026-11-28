package org.kenny.spring;

import org.kenny.spring.dao.CustomerDao;
import org.kenny.spring.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration //这个注解就可以把这个类变为一个配置类，代替配置文件，也会纳入IoC容器的管理，它也是一个bean
@ComponentScan({"org.kenny.spring.dao", "org.kenny.spring.service"}) //参数是一个数组，如果只有一个bean可以用一个字符串
public class SpringConfiguration {
    //把自己new的对象纳入IoC容器管理
    @Bean("date") //这里给bean起名了， 在获取bean的时候就使用这个名字，否则程序崩溃
    public Date getDate(){
       return new Date();
    }
}
