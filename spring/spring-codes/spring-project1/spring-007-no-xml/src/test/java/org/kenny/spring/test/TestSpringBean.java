package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.SpringConfiguration;
import org.kenny.spring.dao.CustomerDao;
import org.kenny.spring.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;


public class TestSpringBean {
    @Test
    public void test01() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        CustomerService service = context.getBean("customerService", CustomerService.class);
        System.out.println(service);
        service.save();
        System.out.println("==================");
        Date date = context.getBean("date", Date.class); //给bean起了名字，就应该这么写
        //Date date = context.getBean("getDate", Date.class); //用这个的前提是没有给bean起名字，起了名字这么写就会崩溃
        System.out.println(date);
    }

}
