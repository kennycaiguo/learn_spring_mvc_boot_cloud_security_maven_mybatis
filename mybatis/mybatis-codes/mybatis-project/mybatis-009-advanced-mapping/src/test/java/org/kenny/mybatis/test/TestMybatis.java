package org.kenny.mybatis.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.kenny.mybatis.entity.Student;
import org.kenny.mybatis.mapper.StudentMapper;
import org.kenny.mybatis.util.SqlSessionUtil;

public class TestMybatis {

    @Test
    public void testSelectBySid(){
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        Student student = mapper.selectBySid(1);
        System.out.println(student);
    }
}
