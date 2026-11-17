package org.kenny.mybatis.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.kenny.mybatis.entity.Car;
import java.util.List;
import java.util.Map;

public interface CarMapper {
    Car selectById(@Param("id") Long id);
    int updateById(Car car);
}

