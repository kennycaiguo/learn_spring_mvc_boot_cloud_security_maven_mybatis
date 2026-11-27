package org.kenny.spring.bean;

import org.springframework.stereotype.Repository;

@Repository //没有配置名称，就使用类名的首字母小写后的名称之外bean的名称此时bean的名称是userDao
public class UserDaoMysql  implements IUserDao{
    @Override
    public String toString() {
        return "UserDaoMysql{Method=insert}";
    }

    @Override
    public void insert() {
        System.out.println("保存用户信息到MySQL数据库成功...");
    }
}
