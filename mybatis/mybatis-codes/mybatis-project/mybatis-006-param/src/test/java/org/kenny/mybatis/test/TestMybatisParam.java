package org.kenny.mybatis.test;

import org.junit.jupiter.api.Test;
import org.kenny.mybatis.entity.Student;
import org.kenny.mybatis.mapper.StudentMapper;
import org.kenny.mybatis.util.SqlSessionUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMybatisParam {
    @Test
    public void testHandleDate() {
        try {
            //根据生日查询学生信息
            String birthday = "1980-10-12";
            //转化为日期对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birth = sdf.parse(birthday);
            StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
            List<Student> students = mapper.selectByBirth(birth);
            students.forEach(System.out::println);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testSelectByNameAndSex() {
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByNameAndSex("张三",'男');
        students.forEach(System.out::println);
    }
    @Test
    public void testSelectByNameAndSex2() {
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByNameAndSex2("张三",'男');
        students.forEach(System.out::println);
    }@Test

    //多参数时，可以使用map
    public void testSelectByNameAndSex3() {
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        Map<String,Object> params = new HashMap<>();
        params.put("name","李四");
        params.put("sex",'女');
        List<Student> students = mapper.selectByNameAndSex3(params);
        students.forEach(System.out::println);
    }
}
