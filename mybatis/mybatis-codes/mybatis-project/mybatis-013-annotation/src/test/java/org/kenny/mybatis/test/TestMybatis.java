package org.kenny.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import org.kenny.mybatis.entity.Clazz;
import org.kenny.mybatis.mapper.ClazzMapper;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.io.IOException;
import java.util.List;

public class TestMybatis {
    @Test
    public void testAdd(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = new Clazz();
//        clazz.setCid(1003);
//        clazz.setCname("高二1班");
        clazz.setCid(1004);
        clazz.setCname("高二2班");
        int count = mapper.insert(clazz);
        System.out.println("成功插入"+count+"条数据");
        sqlSession.commit();
    }
    @Test
    public void testDelete(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        //删除高二2班信息
        int count = mapper.deleteByCid(1004);
        System.out.println("成功删除"+count+"条数据");
        sqlSession.commit();
    }

    @Test
    public void testUpdate(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = new Clazz();
        clazz.setCid(1003); //做update的时候，id必须是存在的，否则更新会失败
        clazz.setCname("樟木头中学高二1班");
        int count = mapper.updateByCid(clazz);
        System.out.println("成功更新"+count+"条数据");
        sqlSession.commit();
    }

    @Test
    public void testSelectByCid(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByCid(1002);
        System.out.println(clazz);
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        List<Clazz> clazzes = mapper.selectAll();
        clazzes.forEach(System.out::println);
    }
}