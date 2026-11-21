package org.kenny.spring.dao.impl;

import org.kenny.spring.dao.OrderDao;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void deleteById(int id) {
        System.out.println("成功删除了id为"+id+"的订单");
    }
}
