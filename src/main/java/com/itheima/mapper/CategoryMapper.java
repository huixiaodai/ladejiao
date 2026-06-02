package com.itheima.mapper;

import com.itheima.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values (#{type},#{name},#{sort},#{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void addCategory(Category category);

    List<Category> getlist(String name, Integer type);

    @Select("select * from category where id = #{id}")
    Category getById(Integer id);

    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Integer id);

    @Select("select * from category where type = #{type} and status = 1 order by sort asc")
    List<Category> getlistAll(Integer type);
}
