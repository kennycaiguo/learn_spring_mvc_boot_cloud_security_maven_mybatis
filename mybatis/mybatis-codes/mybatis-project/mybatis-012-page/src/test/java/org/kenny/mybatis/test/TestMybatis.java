package org.kenny.mybatis.test;

import org.junit.jupiter.api.Test;

import org.kenny.mybatis.entity.Car;
import org.kenny.mybatis.mapper.CarMapper;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMybatis {

   @Test
   void test01(){
      int pageNo=3;
      int pageSize=5;

      Map<String,Object> params = new HashMap<>();
      int startIndex=(pageNo-1)*pageSize;
      params.put("startIndex",startIndex);
      params.put("pageSize",pageSize);
      //mapper
      CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
      List<Car> cars = mapper.selectPage(params);
      cars.forEach(System.out::println);
   }
}