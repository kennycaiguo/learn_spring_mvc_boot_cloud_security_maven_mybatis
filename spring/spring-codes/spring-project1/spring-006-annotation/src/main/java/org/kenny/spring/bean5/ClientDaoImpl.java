package org.kenny.spring.bean5;

import org.springframework.stereotype.Repository;

@Repository("clientDao")
public class ClientDaoImpl implements ClientDao {
    @Override
    public void insert() {
        System.out.println("保存客户数据。。。");
    }
}
