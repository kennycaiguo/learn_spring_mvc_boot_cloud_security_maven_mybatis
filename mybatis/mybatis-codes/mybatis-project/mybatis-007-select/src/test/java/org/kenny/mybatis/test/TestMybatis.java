package org.kenny.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.kenny.mybatis.entity.Car;

import org.kenny.mybatis.mapper.CarMapper;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMybatis {
    @Test
    public void testMybatisSelectById() {
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        Map<String,Object> car =mapper.selectById(1L);
        System.out.println(car);
    }
    @Test
    public void testMybatisSelectCarNum() {
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Map<String,Object>> cars =mapper.selectByCarNum("津k88888");
        cars.forEach(car->{
            System.out.println(car);
        });
    }
    @Test
    public void testMybatisSelectAllRetMap() {
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        Map<Long, Map<String,Object>> cars = mapper.selectAllRetMap();
        cars.forEach((k,v)->{
            System.out.println(k +":"+v);
        });
    }

    @Test
    public void testMybatisSelectAllRetMap2() {
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        Map<Long, Car> cars = mapper.selectAllRetMap2();
        cars.forEach((k,v)->{
            System.out.println(k +":"+v);
        });
    }
//    @Test
//    public void testMybatisMappedSelectByid() {
//        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
//        Car car = mapper.mappedSelectById(2L);
//        System.out.println(car);
//    }
    @Test
    public void  testSelectAllToCamelCase(){
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        Map<Long, Car> cars = mapper.selectAllToCamelCase();
        cars.forEach((k,v)->{
            System.out.println(k +":"+v);
        });
    }

    @Test
    public void testMybatisSelectTotalCount(){
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        Long count = mapper.selectTotalCount();
        System.out.println("总记录条数："+count);
    }
}
