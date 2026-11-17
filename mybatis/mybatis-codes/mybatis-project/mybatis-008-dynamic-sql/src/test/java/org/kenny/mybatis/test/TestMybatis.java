package org.kenny.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.kenny.mybatis.entity.Car;

import org.kenny.mybatis.mapper.CarMapper;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.math.BigDecimal;
import java.util.Arrays;
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

    @Test
    public void testUpdateBySet() {
       Car car = new Car();
       car.setId(22L);
//       car.setCarNum("深C33333");
//       car.setBrand("佳士拿限量版");
       car.setGuidePrice(BigDecimal.valueOf(21.9));
       car.setProduceTime("2023-04-02");
       car.setCarType("新能源");
       SqlSession sqlSession = SqlSessionUtil.openSession();
       CarMapper mapper = sqlSession.getMapper(CarMapper.class);
       int count = mapper.updateWithSet(car);
       System.out.println(count==1?"成功更新一条数据":"更新失败");
       sqlSession.commit();
    }

    @Test
    public void testSelectWithChoose() {
        Car car = new Car();
//        car.setId(22L);
//        car.setCarNum("津k88888");
        car.setBrand("佳士拿限量版");
        car.setGuidePrice(BigDecimal.valueOf(21.9));
//        car.setProduceTime("2023-04-02");
//        car.setCarType("混合动力");
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectWithChoose(car);
        cars.forEach(System.out::println);
    }

    @Test
    public void testSelectWithChoose2() {
        Car car = new Car();
//        car.setId(22L);
//        car.setCarNum("津k88888");
//        car.setBrand("佳士拿限量版");
//        car.setGuidePrice(BigDecimal.valueOf(21.9));
//        car.setProduceTime("2023-04-02");
        car.setCarType("混合动力");
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectWithChoose2(car);
        cars.forEach(System.out::println);
    }

    @Test
    public void testDeleteByForeach() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        long[] ids = {14,16,20,22};
        int count = mapper.deleteByForeach(ids);
        System.out.println(count >=1?"成功删除":"删除失败");
        sqlSession.commit();
    }

    // 测试批量插入数据
    @Test
    public void testInsetByForeach(){
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        Car car1 = new Car(null, "SK2001", "兰博基尼S", new BigDecimal("20.0"), "1998-10-11", "电车");
        Car car2 = new Car(null, "SK2002", "兰博基尼M", new BigDecimal("30.0"), "1998-09-11", "新能源");
        Car car3 = new Car(null, "SK2003", "兰博基尼L", new BigDecimal("40.0"), "1998-08-11", "燃油车");
        List<Car> cars = Arrays.asList(car1, car2, car3);
        int count = mapper.insertByForeach(cars);
        System.out.println("插入了几条记录" + count);
        SqlSessionUtil.openSession().commit();
    }

    @Test
    public void testSelectAllInclude(){
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAllInclude();
        cars.forEach(System.out::println);
    }
}
