package org.kenny.spring.entity;

import java.util.Arrays;

public class Order {
    private String orderId;
    private String clientName;
    private Goods[] goods;


    public Order() {
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setGoods(Goods[] goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", goods=" + Arrays.toString(goods) +
                '}';
    }
}
