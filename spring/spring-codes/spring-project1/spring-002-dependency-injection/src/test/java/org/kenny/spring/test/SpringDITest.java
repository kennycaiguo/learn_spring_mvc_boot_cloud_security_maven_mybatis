package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.controller.OrderController;
import org.kenny.spring.controller.UserController;
import org.kenny.spring.entity.*;
import org.kenny.spring.pool.MyDataSource;
import org.kenny.spring.pool.MyPool;
import org.kenny.spring.service.AccountService;
import org.kenny.spring.service.ClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.kenny.spring.entity.MyMath;

import javax.sql.DataSource;

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
    @Test
    public void testInjectListType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        People people = context.getBean("peopleBean", People.class);
        System.out.println(people);
    }
    @Test
    public void testInjectSetType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        Contact contact = context.getBean("contactBean", Contact.class);
        System.out.println(contact);
    }

    @Test
    public void testInjectMapType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        People people = context.getBean("peopleBean", People.class);
        System.out.println(people);
    }
    @Test
    public void testInjectPropertiesType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        MyConf conf = context.getBean("myConfBean", MyConf.class);
        System.out.println(conf);
    }
    @Test
    public void testInjectEmptyAndNullType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        FakeUser fakeUser = context.getBean("fakeUserBean", FakeUser.class);
        System.out.println(fakeUser);
    }
    @Test
    public void testInjectSpecialCharacter() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-simple-type.xml");
        MyMath math = context.getBean("mathBean", MyMath.class);
        System.out.println(math);
    }

    @Test
    public void testInjectPNamespace(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-p.xml");
        Customer customer = context.getBean("customerBean", Customer.class);
        System.out.println(customer);
    }
    @Test
    public void testInjectCNamespace(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-c.xml");
        Client client = context.getBean("clientBean", Client.class);
        System.out.println(client);
    }

    @Test
    public void testInjectUtilNamespace(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-util.xml");
        DataSource1 dataSource1 = context.getBean("ds1Bean", DataSource1.class);
        DataSource2 dataSource2 = context.getBean("ds2Bean", DataSource2.class);
        System.out.println(dataSource1);
        System.out.println(dataSource2);
    }

    @Test
    public void testInjectAutoWiredByName() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-autowire.xml");
        ClientService service = context.getBean("serviceBean", ClientService.class);
        System.out.println(service);
        service.saveClient();
    }

    @Test
    public void testInjectAutoWiredByType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-autowire.xml");
        AccountService service = context.getBean("accServiceBean", AccountService.class);
        System.out.println(service);
        service.saveAccount();
    }
    @Test
    public void testInjectPropertiesFile() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-properties.xml");
        MyDataSource dataSource = context.getBean("myDsBean", MyDataSource.class);
        System.out.println(dataSource);
    }
}
