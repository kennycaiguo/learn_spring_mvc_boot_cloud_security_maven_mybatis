package org.kenny.mybatis.mapper;

import org.kenny.mybatis.entity.Clazz;

public interface ClazzMapper {
    Clazz selectClazzByCid(Integer cid);
    //一对多
    Clazz selectClazzAndStusByCid(Integer cid);
    Clazz selectClazzAndStusByCidStep(Integer cid);
}
