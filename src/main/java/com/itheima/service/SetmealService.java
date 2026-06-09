package com.itheima.service;

import com.itheima.dto.SetmealDTO;
import com.itheima.dto.SetmealPageQueryDTO;
import com.itheima.result.PageResult;
import com.itheima.vo.SetmealVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SetmealService {
    void addSetmeal(SetmealDTO setmealDTO);

    PageResult<SetmealVO> getSetmealList(SetmealPageQueryDTO setmealPageQueryDTO);

    SetmealVO getSetmealById(Integer id);

    void updateSetmealStatus(Integer id);

    void updateSetmeal(SetmealDTO setmealDTO);

    void deleteBatchSetmeal(List<Integer> ids);
}
