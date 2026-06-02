package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dto.CategoryAddDTO;
import com.itheima.dto.CategoryQueryDTO;
import com.itheima.entity.Category;
import com.itheima.mapper.CategoryMapper;
import com.itheima.result.PageResult;
import com.itheima.result.Result;
import com.itheima.service.CategoryService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(CategoryAddDTO categoryAddDTO) {
        //1.new Category
        Category category = new Category();
        BeanUtils.copyProperties(categoryAddDTO, category);

        category.setStatus(1);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());


        Integer id = ThreadLocalUtil.getCurrentId();
        category.setCreateUser(id);
        category.setUpdateUser(id);

        categoryMapper.addCategory(category);

    }

    /**
     * 分类分页查询
     * @param categoryQueryDTO
     * @return
     */
    @Override
    public PageResult<Category> getlist(CategoryQueryDTO categoryQueryDTO) {
        //1.pageHelper
        PageHelper.startPage(categoryQueryDTO.getPage(),categoryQueryDTO.getPageSize());

        //2.查询
        List<Category> categoryList = categoryMapper.getlist(categoryQueryDTO.getName(),categoryQueryDTO.getType());

        //3.将categoryList转为page
        Page<Category> p = (Page<Category>) categoryList;

        //4.new1个pageResult
        PageResult<Category> pageResult = new PageResult<>();
        pageResult.setTotal(p.getTotal());
        pageResult.setRecords(p.getResult());
        return pageResult;
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    /**
     * 起售、停售分类
     * @param id
     */
    @Override
    public void updateStatus(Integer id) {
        Integer currentId = ThreadLocalUtil.getCurrentId();
        Category category = categoryMapper.getById(id);

        Integer status = category.getStatus();

        category.setStatus(status == 1 ? 2 : 1);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(currentId);

        categoryMapper.update(category);
    }

    @Override
    public void update(CategoryAddDTO categoryAddDTO) {
        Integer currentId = ThreadLocalUtil.getCurrentId();
        Category category = new Category();
        BeanUtils.copyProperties(categoryAddDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(currentId);

        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }

    //给新增菜品的时候，要一个接口，来查所有的分类
    @Override
    public List<Category> getListAll(Integer type) {
        return categoryMapper.getlistAll(type);
    }
}
