package org.kenny.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.kenny.mybatis.entity.Clazz;
import org.kenny.mybatis.entity.Student;
import org.kenny.mybatis.mapper.ClazzMapper;
import org.kenny.mybatis.mapper.StudentMapper;
import org.kenny.mybatis.util.SqlSessionUtil;

public class TestMybatis {

    @Test
    public void testSelectBySid(){
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        Student student = mapper.selectBySid(1);
        System.out.println(student);
    }
    @Test
    public void selectStudentAndClazzBySid(){
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        Student student = mapper.selectStudentAndClazzBySid(1);
        System.out.println(student.getSname());
//        System.out.println(student.getClazz().getCname());
    }
    @Test
    public void testSelectClazzAndStusByCid(){
        ClazzMapper mapper = SqlSessionUtil.openSession().getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectClazzAndStusByCid(1001);
        System.out.println(clazz);
    }
    @Test
    public void testSelectClazzAndStusByCidLazy(){
        ClazzMapper mapper = SqlSessionUtil.openSession().getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectClazzAndStusByCidStep(1001);
        System.out.println(clazz.getCname());
        System.out.println(clazz.getStudents().get(1).getSname());
    }
}
