package org.kenny.mybatis.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
   @Test
   public void testPageHelper(){
      CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
      int pageNo=3;
      int pageSize=5;
      PageHelper.startPage(pageNo,pageSize);
      List<Car> cars = mapper.selectAll();
      PageInfo<Car> pageInfo = new PageInfo<>(cars,2);
//      System.out.println(pageInfo.getTotal());
      pageInfo.getList().forEach(System.out::println); //遍历输出查询到的数据集合
   }
}