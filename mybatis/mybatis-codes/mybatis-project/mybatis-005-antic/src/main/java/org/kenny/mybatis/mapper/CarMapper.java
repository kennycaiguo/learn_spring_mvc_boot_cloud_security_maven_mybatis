package org.kenny.mybatis.mapper;

import org.kenny.mybatis.entity.Car;

import java.util.List;

public interface CarMapper {
    public List<Car> selectAllByTableName(String tableName) ;

    public int insertCar(Car car);

    public int updateById(int id,Car car);

    public List<Car> selectByCarNum(String carNum);

    public int deleteById(int id);

    public Car selectById(int id);
}
