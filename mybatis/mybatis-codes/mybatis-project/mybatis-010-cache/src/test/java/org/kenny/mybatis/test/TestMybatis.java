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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMybatis {
    @Test
    public void testMybatisCache1() throws IOException {
        //注意这里不能使用工具类
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car =mapper.selectById(5L);
        System.out.println(car);
        //清理缓存
//        sqlSession.clearCache();
        //在两次查询之间运行一条更新语句
        Car car1 = new Car();
        car1.setId(3L);
        car1.setBrand("宝龙666");
        mapper.updateById(car1);
        sqlSession.commit();
        //执行相同的语句看看会不会再次输出sql语句
        Car car2 = mapper.selectById(5L);
        System.out.println(car2);
    }

    @Test
    public void testUpdateById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car1 = new Car();
        car1.setId(3L);
        car1.setBrand("宝龙999");
        int count = mapper.updateById(car1);
        System.out.println(count);
        sqlSession.commit();
    }
}