package org.kenny.mybatis.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.kenny.mybatis.entity.Car;
import java.util.List;
import java.util.Map;

public interface CarMapper {

    /**
     *
     * @param params
     *        可能的查询条件是
     *        1.brand，支持模糊查询
     *        2.guide_Price
     *        3.car_type
     * @return 一个查询结果的列表
     */
    List<Car> selectByCondition(Map<String, Object> params);
    List<Car> selectByConditionWithWhere(Map<String, Object> params);
    List<Car> selectByConditionWithTrim(Map<String, Object> params);
}

