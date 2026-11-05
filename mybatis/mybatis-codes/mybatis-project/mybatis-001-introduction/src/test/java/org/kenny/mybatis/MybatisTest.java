package org.kenny.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.io.InputStream;

public class MybatisTest {
    @Test
    public void testFirst() {
        try(InputStream in = Resources.getResourceAsStream("mybatis-config.xml");){
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            try (SqlSession session = factory.openSession()) {
                int count = session.insert("insertCar");
                System.out.println(count);
                //提交
                session.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
         }
    }
    @Test
    public void testSqlSessionUtil() {
        try (SqlSession sqlSession = SqlSessionUtil.openSession()) {
            sqlSession.insert("insertCar");
            //别忘了提交
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
