package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringBean {
    @Test
    public void test01() {
       //实例化spring容器的上下文对象,通过加载类路径下面的beans.xml配置文件来实现
        ApplicationContext appContext=new ClassPathXmlApplicationContext("beans.xml");
        //根据我们设置的bean的id来获取bean对象
        Object userBean = appContext.getBean("userBean");
        System.out.println(userBean);
//        Object vip =  appContext.getBean("vipBean");
//        System.out.println(vip);
    }
    @Test
    public void test02() {
        ApplicationContext appContext=new ClassPathXmlApplicationContext("xml/app.xml");
        Object vipBean = appContext.getBean("vipBean");
        System.out.println(vipBean);
    }

    @Test
    public void test03() {
        ApplicationContext appContext=new ClassPathXmlApplicationContext("beans.xml","xml/app.xml");
        Object userBean = appContext.getBean("userBean");
        System.out.println(userBean);
        Object vip =  appContext.getBean("vipBean");
        System.out.println(vip);
    }
}
