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
        try(SqlSession sqlSession = SqlSessionUtil.openSession()) {
            List<Car> cars= sqlSession.selectList("selectByCarNum", "津k88888");
            System.out.println(cars);
        }catch (Exception e) {
            e.printStackTrace();
        }
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
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            //修改id为7的记录的数据
            Car car = new Car();
            car.setId(7L);
            car.setCarNum("莞B55558");
            car.setBrand("红旗444");
            car.setGuidePrice(new BigDecimal("35.50"));
            car.setProduceTime("2024-12-24");
            car.setCarType("新能源");
            int count = sqlSession.update("updateById", car);
            System.out.println(count==1?"成功更新"+count+"条记录":"更新数据失败");
            //手动提交
            sqlSession.commit();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testMybatisDeleteById() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            int count = sqlSession.delete("deleteById", 18);
            System.out.println(count==1?"删除成功，删除了"+count+"条记录":"删除失败");
            //提交事务
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testMybatisSelectById() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
             Car car = sqlSession.selectOne("selectById", 14L);
             System.out.println(car);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
