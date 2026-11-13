package org.kenny.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kenny.mybatis.entity.Car;

public class TestMybatisCrud {
    @Test
    public void testMybatisInsert() {
        //1.准备数据，这里我们把数据放到map集合中
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("car_num","津b88888");
        data.put("brand","本田雅阁");
        data.put("guide_price",new BigDecimal("25.50"));
        data.put("produce_time","2024-10-24");
        data.put("car_type","新能源");

        //2.保存数据
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            int count = sqlSession.insert("insertCar", data);
            System.out.println(count == 1?"保存成功":"保存失败");
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMybatisInsertEntity() {
        Car car = new Car("深A66666","丰田佳美",new BigDecimal("19.80"),
                "2024-12-22","混合动力");
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            int count = sqlSession.insert("insertCar2", car);
            System.out.println(count == 1?"保存成功":"保存失败");
            sqlSession.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testMybatisDeleteById() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            int count = sqlSession.delete("deleteById", "15");
            System.out.println(count==1?"删除成功，删除了"+count+"条记录":"删除失败");
            //提交事务
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //利用id来更新数据
    @Test
    public void testMybatisUpdateById() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            //修改id为7的记录的数据
            Car car = new Car();
            car.setId(7L);
            car.setCarNum("莞B55555");
            car.setBrand("红旗2");
            car.setGuidePrice(new BigDecimal("55.50"));
            car.setProduceTime("2024-10-24");
            car.setCarType("新能源");
//            int count = sqlSession.update("updateById", car);
            int count = sqlSession.update("updateById2", car);
            System.out.println(count==1?"成功更新"+count+"条记录":"更新数据失败");
            //手动提交
            sqlSession.commit();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testMybatisSelectById(){
        try(SqlSession sqlSession = SqlSessionUtil.openSession()) {
            Car o = (Car)sqlSession.selectOne("selectById", 7L);
            System.out.println(o);
        }catch (Exception e) {
            e.printStackTrace();
        }
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
    public void testMybatisSelectAll(){
        try(SqlSession sqlSession = SqlSessionUtil.openSession()) {
            List<Car> cars= sqlSession.selectList("selectAll");
            cars.forEach(System.out::println);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMybatisNameSpace(){
        try(SqlSession sqlSession = SqlSessionUtil.openSession()) {
            Car o = (Car)sqlSession.selectOne("Car1.selectById", 7L);
            System.out.println(o);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
