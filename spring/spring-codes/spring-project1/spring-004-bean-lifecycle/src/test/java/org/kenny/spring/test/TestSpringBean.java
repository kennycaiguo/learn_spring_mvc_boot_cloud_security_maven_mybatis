package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.bean.Client;
import org.kenny.spring.bean.User;
import org.kenny.spring.bean.Vip;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringBean {
    @Test
    public void test01() {
        //如果需要测试最后一步，需要使用ClassPathXmlApplicationContext对象替代 ApplicationContext
        ClassPathXmlApplicationContext appContext=new ClassPathXmlApplicationContext("appContext.xml");
        //根据我们设置的bean的id来获取bean对象
        User user = appContext.getBean("userBean", User.class);
        System.out.println(user);
        System.out.println("===============================");
        //测试最后一步需要手动关闭IoC容器
        appContext.close();
    }

    @Test
    public void test02() {
        //如果需要测试最后一步，需要使用ClassPathXmlApplicationContext对象替代 ApplicationContext
        ClassPathXmlApplicationContext appContext=new ClassPathXmlApplicationContext("appContext.xml");
        //根据我们设置的bean的id来获取bean对象
        Client client = appContext.getBean("clientBean", Client.class);
        System.out.println(client);
        //测试最后一步需要手动关闭IoC容器
        appContext.close();
    }

    @Test
    public void test03() {
        Vip vip = new Vip();
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("vipBean", vip);
        Vip vip1 = beanFactory.getBean("vipBean", Vip.class);
        System.out.println(vip);
        System.out.println(vip1);
    }
}
