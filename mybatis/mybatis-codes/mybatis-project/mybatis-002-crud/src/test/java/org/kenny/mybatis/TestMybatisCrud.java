package org.kenny.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.math.BigDecimal;
import java.util.HashMap;
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
}
