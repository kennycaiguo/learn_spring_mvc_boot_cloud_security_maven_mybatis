package org.kenny.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.kenny.mybatis.entity.Student;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StudentMapper {
   List<Student> selectByBirth(Date birth);
   List<Student> selectByNameAndSex(String name, Character sex);
   List<Student> selectByNameAndSex2(@Param("name")String name, @Param("sex")Character sex);
   List<Student> selectByNameAndSex3(Map<String, Object> params);
}
