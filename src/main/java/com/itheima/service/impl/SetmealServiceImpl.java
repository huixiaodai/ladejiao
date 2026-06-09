package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dto.SetmealDTO;
import com.itheima.dto.SetmealPageQueryDTO;
import com.itheima.entity.Setmeal;
import com.itheima.entity.SetmealDish;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.SetmealDishMapper;
import com.itheima.mapper.SetmealMapper;
import com.itheima.result.PageResult;
import com.itheima.service.SetmealService;
import com.itheima.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setmeal.setStatus(1);

        setmealMapper.insert(setmeal);

        //获得到前端的一堆加入的菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {

            List<SetmealDish> setmealDishList = new ArrayList<>();

            for (SetmealDish setmealDish : setmealDishes) {
                SetmealDish dish = new SetmealDish();

                dish.setSetmealId(setmeal.getId());
                dish.setDishId(setmealDish.getDishId());
                dish.setName(setmealDish.getName());
                dish.setPrice(setmealDish.getPrice());
                dish.setCopies(setmealDish.getCopies());
                setmealDishList.add(dish);
            }
            setmealDishMapper.addBatchSetmealDish(setmealDishList);
        }
    }

    @Override
    public PageResult<SetmealVO> getSetmealList(SetmealPageQueryDTO setmealPageQueryDTO) {
        //1.开启pageHelpher
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        //2.进行查询
        Page<SetmealVO> setmealList = setmealMapper.getSetmealList(setmealPageQueryDTO);
        return new PageResult<>(setmealList.getTotal(),setmealList.getResult());
    }

    @Override
    public SetmealVO getSetmealById(Integer id) {
        SetmealVO setmeal = setmealMapper.getSetmealById(id);

        setmeal.setSetmealDishes(setmealDishMapper.getSetmealDishById(id));
        return setmeal;
    }

    @Override
    public void updateSetmealStatus(Integer id) {
        SetmealVO setmealVO = setmealMapper.getSetmealById(id);
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealVO, setmeal);

        Integer status = setmealVO.getStatus() == 1 ? 0 : 1;
        setmeal.setStatus(status);
        setmealMapper.updateSetmeal(setmeal);
    }

    @Override
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO) {
        // 1.修改套餐基础内容
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.updateSetmeal(setmeal);

        // 2.修改套餐菜品
        // 2.1先删除套餐菜品所有菜品
        setmealDishMapper.deleteBySetmealId(setmeal.getId());
        // 2.2把修改的套餐加进来
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            List<SetmealDish> setmealDishList = new ArrayList<>();
            for (SetmealDish setmealDish : setmealDishes) {
                SetmealDish dish = new SetmealDish();
                dish.setSetmealId(setmeal.getId());
                dish.setDishId(setmealDish.getDishId());
                dish.setName(setmealDish.getName());
                dish.setPrice(setmealDish.getPrice());
                dish.setCopies(setmealDish.getCopies());
                setmealDishList.add(dish);
            }
            setmealDishMapper.addBatchSetmealDish(setmealDishList);
        }
    }

    @Override
    @Transactional
    public void deleteBatchSetmeal(List<Integer> ids) {
        // 起售中的套餐不能删除
//        for (Integer id : ids) {
//            SetmealVO setmeal = setmealMapper.getSetmealById(id);
//            Integer status = setmeal.getStatus();
//            if (status == 1) {
//                throw new BusinessException("起售中的套餐不可以删除");
//            }
//        }
//
//        for (Integer id : ids) {
//            // 先删除套餐中的菜品
//            setmealDishMapper.deleteBySetmealId(id);
//            // 在删除套餐
//            setmealMapper.deleteById(id);
//        }

        // 最优解法
        if (ids == null || ids.isEmpty()) {
            return;
        }

        // 1. 查询这些套餐里是否有起售状态
        for (Integer id : ids) {
            SetmealVO setmeal = setmealMapper.getSetmealById(id);
            Integer status = setmeal.getStatus();
            if (status == 1) {
                throw new BusinessException("起售中的套餐不可以删除");
            }
        }

        // 2.先批量删除套餐菜品关系
        setmealDishMapper.deleteBatchBySetmealIds(ids);
        // 3. 再批量删除套餐
        setmealMapper.deleteBatchByIds(ids);

    }
}
