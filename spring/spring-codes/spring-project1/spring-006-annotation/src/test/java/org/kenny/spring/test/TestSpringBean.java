package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.bean.*;
import org.kenny.spring.bean2.OrderController;
import org.kenny.spring.bean4.User;
import org.kenny.spring.bean5.ClientController;
import org.kenny.spring.bean5.ClientService;
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
    @Test
    public void test02() {
        ApplicationContext context=new ClassPathXmlApplicationContext("appContext.xml");
        OrderController orderController = context.getBean("orderController", OrderController.class);
        System.out.println(orderController);
    }
    @Test
    public void test03() {
        ApplicationContext context=new ClassPathXmlApplicationContext("appContext2.xml");
        //因为我们在类的构造函数里面有输出，我们只需要一句话就可以测试

    }
    @Test
    public void test04() {
        ApplicationContext context=new ClassPathXmlApplicationContext("appContext3.xml");
        User user = context.getBean("user", User.class);
        System.out.println(user);
    }
    @Test
    public void testAutowired() {
        ApplicationContext context=new ClassPathXmlApplicationContext("appContext4.xml");
        UserController controller = context.getBean("userController", UserController.class);
        System.out.println(controller);
        controller.saveData();
    }
    @Test
    public void testLombok() {
        Customer customer = new Customer();
        customer.setName("Jade");
        customer.setAge(18);
        customer.setId(1001L);
        System.out.println(customer);
        System.out.println(customer.getAge());
    }
    @Test
    public void test05() {
        ApplicationContext context=new ClassPathXmlApplicationContext("appContext4.xml");
        MyService myService = context.getBean("myService", MyService.class);
        System.out.println(myService);
        myService.save();
    }

    @Test
    public void test06() {
        ApplicationContext context=new ClassPathXmlApplicationContext("appContext5.xml");
        ClientController controller = context.getBean("clientController", ClientController.class);
        System.out.println(controller);
        controller.saveClient();
    }
}
