package com.itheima.service;

import com.itheima.dto.CategoryAddDTO;
import com.itheima.dto.CategoryQueryDTO;
import com.itheima.entity.Category;
import com.itheima.result.PageResult;
import com.itheima.result.Result;

import java.util.List;

public interface CategoryService {
    void add(CategoryAddDTO categoryAddDTO);

    PageResult<Category> getlist(CategoryQueryDTO categoryQueryDTO);

    Category getById(Integer id);

    void updateStatus(Integer id);

    void update(CategoryAddDTO categoryAddDTO);

    void delete(Integer id);

    List<Category> getListAll(Integer type);
}
