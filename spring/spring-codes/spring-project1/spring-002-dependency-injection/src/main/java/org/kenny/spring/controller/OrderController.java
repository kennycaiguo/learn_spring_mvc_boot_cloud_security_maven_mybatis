package org.kenny.spring.controller;

import org.kenny.spring.service.OrderService;

public class OrderController {
    private OrderService orderService;

    //使用构造函数来赋值
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    public void deleteOrder(int id) {
        System.out.println("OrderController.deleteOrder start...");
        orderService.delete(id);
        System.out.println("OrderController.deleteOrder end...");
    }
}
