package org.kenny.spring.service.impl;

import org.kenny.spring.dao.OrderDao;
import org.kenny.spring.dao.impl.OrderDaoImpl;
import org.kenny.spring.service.OrderService;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    //使用带参数构造方法来给属性赋值
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
//    //提供无参构造函数
//    public OrderServiceImpl() {
//    }

    @Override
    public void delete(int id) {
        System.out.println("Order Service delete begin...");
         orderDao.deleteById(id);
        System.out.println("Order Service delete end...");
    }
}
