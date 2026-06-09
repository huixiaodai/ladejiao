package com.itheima.mapper;

import com.itheima.entity.Dish;
import com.itheima.entity.DishFlavor;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void addBatchFlavor(List<DishFlavor> flavors);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getByDishId(Integer id);

    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteByDishId(@NotNull(groups = Update.class) Integer id);

    void deleteBatchByIds(List<Integer> ids);
}
