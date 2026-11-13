package org.kenny.mybatis.mapper;

import org.kenny.mybatis.entity.Car;
import java.util.List;

public interface CarMapper {
    List<Car> selectAllByTableName(String tableName) ;

    int insertCar(Car car);

    int updateById(Car car);

    List<Car> selectByCarNum(String carNum);

    int deleteById(int id);

    Car selectById(int id);

    List<Car> selectCarByBrand(String brand);
    List<Car> selectCarByBrand2(String brand);

    void insertAndReturnPk(Car car);
}
