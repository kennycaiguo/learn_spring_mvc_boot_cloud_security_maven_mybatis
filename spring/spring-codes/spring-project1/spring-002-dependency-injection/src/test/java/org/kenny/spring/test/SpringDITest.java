package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.controller.OrderController;
import org.kenny.spring.controller.UserController;
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
}
