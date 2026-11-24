package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.controller.OrderController;
import org.kenny.spring.controller.UserController;
import org.kenny.spring.entity.Order;
import org.kenny.spring.entity.Person;
import org.kenny.spring.entity.Student;
import org.kenny.spring.entity.User;
import org.kenny.spring.pool.MyPool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDITest {
    @Test
    public void testDI() {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        UserController userController = context.getBean("ctrlBean", UserController.class);
        userController.saveUser();
    }
    @Test
    public void testDI2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        OrderController orderCtrl = context.getBean("orderCtrlBean", OrderController.class);
        orderCtrl.deleteOrder(1);
    }
    @Test
    public void testInTernalBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext2.xml");
        UserController ctrl = context.getBean("ctrlBean", UserController.class);
        ctrl.saveUser();
    }
    @Test
    public void testInjectSimpleType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        User user = context.getBean("userBean", User.class);
        System.out.println(user);
    }
    @Test
    public void testInjectSimpleType2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        MyPool mypool = context.getBean("myPoolBean", MyPool.class);
        System.out.println(mypool);
    }
    @Test
    public void testInjectCascadeSimpleType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        Student student = context.getBean("studentBean", Student.class);
        System.out.println(student);
    }

    @Test
    public void testInjectArrayType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        Person person = context.getBean("personBean", Person.class);
        System.out.println(person);
    }
    @Test
    public void testInjectArrayType2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        Order order = context.getBean("orderBean", Order.class);
        System.out.println(order);
    }
}
