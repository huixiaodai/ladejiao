package com.itheima.service;

import com.itheima.dto.DishDTO;
import com.itheima.dto.DishQueryDTO;
import com.itheima.entity.Dish;
import com.itheima.result.PageResult;
import com.itheima.result.Result;
import com.itheima.vo.DishVO;

import java.util.List;

public interface DishService {
    void addDish(DishDTO dishDTO);


    PageResult<DishVO> getDishList(DishQueryDTO dishQueryDTO);

    void updateDishStatus(Integer id);

    void updateDish(DishDTO dishDTO);

    DishVO getdish(Integer id);

    void deleteBatchDish(List<Integer> ids);
}
