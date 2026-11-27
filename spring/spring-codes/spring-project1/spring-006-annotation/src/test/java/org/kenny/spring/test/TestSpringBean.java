package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.bean.UserController;
import org.kenny.spring.bean.UserDao;
import org.kenny.spring.bean.UserService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestSpringBean {
    @Test
    public void test01() {
       //实例化spring容器的上下文对象,通过加载类路径下面的beans.xml配置文件来实现
        ApplicationContext context=new ClassPathXmlApplicationContext("appContext.xml");
        UserController controller = context.getBean("userController", UserController.class);
        UserService service = context.getBean("userService", UserService.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        System.out.println(controller);
        System.out.println(service);
        System.out.println(userDao);
    }

}
