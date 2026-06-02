package com.itheima.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dto.DishDTO;
import com.itheima.dto.DishFlavorDTO;
import com.itheima.dto.DishQueryDTO;
import com.itheima.entity.Dish;
import com.itheima.entity.DishFlavor;
import com.itheima.mapper.CategoryMapper;
import com.itheima.mapper.DishFlavorMapper;
import com.itheima.mapper.DishMapper;
import com.itheima.result.PageResult;
import com.itheima.service.DishService;
import com.itheima.utils.ThreadLocalUtil;
import com.itheima.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    @Transactional
    public void addDish(DishDTO dishDTO) {
        //1.new Dish
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);


        dish.setStatus(1);
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        Integer currentId = ThreadLocalUtil.getCurrentId();
        dish.setCreateUser(currentId);
        dish.setUpdateUser(currentId);

        //2.把这条菜品新增好，然后获得dishId,然后再去口味表插入数据
        dishMapper.addDish(dish);

        //3.保存dish_flavors子表
        //3.1我们从前端得到的是DishFlavorDTO 有name和value value并且是数组类型的List<String>
        List<DishFlavorDTO> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {

            //3.2DishFlavor是我们真正要传进去的菜品口味表的东西 id,dishid,name,value
            List<DishFlavor> flavorList = new ArrayList<>();

            //把 flavors 这个口味集合一个一个拿出来处理。 假设 flavors 里有两条：辣度、温度。那这个循环就会跑两次。
            for (DishFlavorDTO flavorDTO : flavors) {
                DishFlavor flavor = new DishFlavor();
                flavor.setDishId(dish.getId());
                flavor.setName(flavorDTO.getName());
                flavor.setValue(JSON.toJSONString(flavorDTO.getValue()));
                flavorList.add(flavor);
            }
            dishFlavorMapper.addBatchFlavor(flavorList);

        }
    }

    @Override
    public PageResult<DishVO> getDishList(DishQueryDTO dishQueryDTO) {
        //1.开启pageHelper
        PageHelper.startPage(dishQueryDTO.getPage(),dishQueryDTO.getPageSize());

        //2.查询
        List<DishVO> dishList = dishMapper.getDishList(dishQueryDTO.getName(),dishQueryDTO.getCategoryId(),dishQueryDTO.getStatus());

        //3.强转为page
        Page<DishVO> page = (Page<DishVO>) dishList;


        return new PageResult<>(page.getTotal(),page.getResult());

    }
}
