package org.kenny.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.kenny.mybatis.entity.Car;

import org.kenny.mybatis.mapper.CarMapper;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.math.BigDecimal;
import java.util.List;

public class TestMybatis {
    @Test
    public void testSelectAllByTableName() {
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAllByTableName("t_car");
        cars.forEach(System.out::println);
    }

    @Test
    public void testMybatisSelectByCarNum(){
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByCarNum("津k88888");
        cars.forEach(System.out::println);
    }

    @Test
    public void testInsertCar() {
        Car car = new Car();
        car.setCarNum("成B22222");
        car.setBrand("长安333");
        car.setGuidePrice(new BigDecimal("12.0"));
        car.setCarType("电车");
        car.setProduceTime("2020-05-20");
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        int count = mapper.insertCar(car);
        System.out.println(count==1?"插入成功":"插入失败");
        sqlSession.commit();

    }
    @Test
    public void testMybatisUpdateById() {
        Car car = new Car();
        car.setId(7L);
        car.setCarNum("莞B55558");
        car.setBrand("暴龙555");
        car.setGuidePrice(new BigDecimal("35.50"));
        car.setProduceTime("2024-12-24");
        car.setCarType("新能源");
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        int count = mapper.updateById(car); //这里不需要一个id作为参数，因为car对象里面就有id
        System.out.println(count==1?"成功更新"+count+"条记录":"更新数据失败");
        sqlSession.commit();
    }
    @Test
    public void testMybatisDeleteById() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
         int count = mapper.deleteById(17);
         System.out.println(count==1?"删除成功，删除了"+count+"条记录":"删除失败");
         sqlSession.commit();
    }

    @Test
    public void testMybatisSelectById() {
        Car car = SqlSessionUtil.openSession().getMapper(CarMapper.class).selectById(1);
        System.out.println(car);
    }
    @Test
    public void testMybatisSelectByBrand() {
        //根据品牌进行模糊查找
//        List<Car> cars = SqlSessionUtil.openSession().getMapper(CarMapper.class).selectCarByBrand("田");
        List<Car> cars = SqlSessionUtil.openSession().getMapper(CarMapper.class).selectCarByBrand2("田");
        cars.forEach(System.out::println);
    }

    @Test
    public void testInsertAndReturnPk() {
        Car car = new Car();
        car.setCarNum("莞B55588");
        car.setBrand("兰博基尼");
        car.setGuidePrice(new BigDecimal("35.50"));
        car.setProduceTime("2025-12-24");
        car.setCarType("新能源");
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        mapper.insertAndReturnPk(car);
        System.out.println(car.getId());
        sqlSession.commit();

    }
}
