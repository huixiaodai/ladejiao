package com.itheima.mapper;

import com.itheima.annotation.AutoFill;
import com.itheima.entity.Dish;
import com.itheima.enumeration.OperationType;
import com.itheima.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @AutoFill(OperationType.INSERT)
    @Insert("INSERT INTO dish (name, category_id, price, image, description,status, create_time, update_time, create_user, update_user)" +
            "values (#{name}, #{categoryId}, #{price}, #{image}, #{description}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @Options(useGeneratedKeys = true,keyProperty = "id") //这样插入后：dish.getId()才有值
    void addDish(Dish dish);

    List<DishVO> getDishList(String name, Integer categoryId, Integer status);

    @Select("select * from dish where id = #{id}")
    Dish getDishById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    void deleteBatchByIds(List<Integer> ids);
}
