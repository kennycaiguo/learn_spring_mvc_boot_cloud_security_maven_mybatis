package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.beancreate.Order;
import org.kenny.spring.beancreate.Person;
import org.kenny.spring.beancreate.Star;
import org.kenny.spring.beancreate.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanCreateTest {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        Star star = context.getBean("starBean", Star.class);
        System.out.println(star);
    }
    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        Order order = context.getBean("orderBean", Order.class);
        System.out.println(order);
    }
    @Test
    public void testFactoryBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        Person person = context.getBean("personBean", Person.class);
        System.out.println(person);
    }
    @Test
    public void testCustomDateFactoryBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        Student student = context.getBean("studentBean", Student.class);
        System.out.println(student);
    }
}
