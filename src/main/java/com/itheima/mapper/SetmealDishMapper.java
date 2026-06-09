package com.itheima.mapper;

import com.itheima.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    void addBatchSetmealDish(List<SetmealDish> setmealDishList);

    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getSetmealDishById(Integer setmealId);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Integer id);

    void deleteBatchBySetmealIds(List<Integer> ids);

    @Select("select * from setmeal_dish where dish_id = #{id}")
    SetmealDish getSetmealDishByDishId(Integer id);
}
