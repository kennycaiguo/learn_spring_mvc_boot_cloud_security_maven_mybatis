package org.kenny.spring.dao;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {
    public void insert(){
        System.out.println("保存客人信息。。。");
    }
}
