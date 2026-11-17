package org.kenny.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import org.kenny.mybatis.entity.Car;
import org.kenny.mybatis.mapper.CarMapper;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.io.IOException;

public class TestMybatis {
    @Test
    public void test01(){
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        Car car = mapper.selectByPrimaryKey(1L);
        System.out.println(car);
    }
}