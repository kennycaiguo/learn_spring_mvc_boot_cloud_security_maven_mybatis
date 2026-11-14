package org.kenny.mybatis.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.kenny.mybatis.entity.Car;
import java.util.List;
import java.util.Map;

public interface CarMapper {

    Map<String, Object> selectById(long id);
    List<Map<String, Object>> selectByCarNum(String carNum);
    @MapKey("id")
    Map<Long, Map<String, Object>> selectAllRetMap();
    @MapKey("id")
    Map<Long, Car> selectAllRetMap2();
    //测试resultMap
    Car mappedSelectById(Long id);
    //测试下划线转驼峰
    @MapKey("id")
    Map<Long, Car> selectAllToCamelCase();
    //返回总记录条数
    Long selectTotalCount();
}

//