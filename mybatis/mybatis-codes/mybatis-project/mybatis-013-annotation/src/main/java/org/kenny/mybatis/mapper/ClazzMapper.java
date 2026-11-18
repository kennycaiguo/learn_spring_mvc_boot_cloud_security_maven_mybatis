package org.kenny.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.kenny.mybatis.entity.Clazz;

import java.util.List;

public interface ClazzMapper {
    //增删改查方法
    @Insert("insert into t_clazz values (#{cid},#{cname})")
    int insert(Clazz clazz);

    @Delete("delete from t_clazz where cid=#{cid}")
    int deleteByCid(@Param("cid") Integer cid);

    @Update("update t_clazz set cname=#{cname} where cid=${cid}")
    int updateByCid(Clazz clazz);

    @Select("select * from t_clazz where cid=#{cid}")
    Clazz selectByCid(@Param("cid") Integer cid);
    //获取所有数据
    @Select("select * from t_clazz")
    List<Clazz> selectAll();
}
