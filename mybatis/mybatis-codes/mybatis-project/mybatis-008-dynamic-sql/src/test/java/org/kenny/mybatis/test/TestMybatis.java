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
    public void testSelectByCondition() {
        Map<String, Object> params = new HashMap<>();
        params.put("brand","本田");
//        params.put("guidePrice",20);
//        params.put("carType","新能源");

        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByCondition(params);
        for (Car car : cars) {
            System.out.println(car);
        }
    }
    @Test
    public void testSelectByConditionWithWhere() {
        Map<String, Object> params = new HashMap<>();
        params.put("brand","本田");
//        params.put("guidePrice",20);
        params.put("carType","新能源");

        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByConditionWithWhere(params);
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    @Test
    public void testSelectByConditionWithTrim() {
        Map<String, Object> params = new HashMap<>();
//        params.put("brand","本田");
        params.put("guidePrice",20);
//        params.put("carType","新能源");

        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByConditionWithTrim(params);
        for (Car car : cars) {
            System.out.println(car);
        }
    }

}
