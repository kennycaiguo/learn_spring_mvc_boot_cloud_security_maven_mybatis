package org.kenny.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.kenny.mybatis.entity.Student;

public interface StudentMapper {
    //根据学生编号查询学生信息，顺便把班级的信息一起查询出来
    Student selectBySid(@Param("sid") Integer sid);
}
