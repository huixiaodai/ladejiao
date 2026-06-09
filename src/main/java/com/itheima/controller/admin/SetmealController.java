package com.itheima.controller.admin;

import com.itheima.dto.SetmealDTO;
import com.itheima.dto.SetmealPageQueryDTO;
import com.itheima.entity.Setmeal;
import com.itheima.result.PageResult;
import com.itheima.result.Result;
import com.itheima.service.SetmealService;
import com.itheima.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult<SetmealVO>> getSetmealList(SetmealPageQueryDTO setmealPageQueryDTO){
        return Result.success(setmealService.getSetmealList(setmealPageQueryDTO));
    }

    @GetMapping("/{id}")
    public Result<SetmealVO> getSetmealById(@PathVariable Integer id){
        return Result.success(setmealService.getSetmealById(id));
    }

    @PutMapping("/status/{id}")
    public Result updateSetmealStatus(@PathVariable Integer id){
        setmealService.updateSetmealStatus(id);
        return Result.success();
    }

    @PutMapping
    public Result updateSetmeal(@RequestBody @Validated(SetmealDTO.Update.class) SetmealDTO setmealDTO){
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteBatchSetmeal(@RequestParam List<Integer> ids){
        setmealService.deleteBatchSetmeal(ids);
        return Result.success();
    }

}
