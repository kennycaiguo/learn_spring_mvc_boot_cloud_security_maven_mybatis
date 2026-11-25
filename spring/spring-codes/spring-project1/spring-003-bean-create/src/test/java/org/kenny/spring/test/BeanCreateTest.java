package org.kenny.spring.test;

import org.junit.jupiter.api.Test;
import org.kenny.spring.beancreate.Order;
import org.kenny.spring.beancreate.Star;
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
    public void test2()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        Order order = context.getBean("orderBean", Order.class);
        System.out.println(order);
    }
}
