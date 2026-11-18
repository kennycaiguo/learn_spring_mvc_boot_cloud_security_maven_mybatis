package org.kenny.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.kenny.mybatis.entity.Car;

import java.util.List;
import java.util.Map;

public interface CarMapper {

    List<Car> selectPage(Map<String, Object> params);

    List<Car> selectAll();
}

