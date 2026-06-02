package com.itheima.controller.admin;

import com.itheima.dto.CategoryAddDTO;
import com.itheima.dto.CategoryQueryDTO;
import com.itheima.dto.DishDTO;
import com.itheima.dto.DishQueryDTO;
import com.itheima.entity.Category;
import com.itheima.entity.Dish;
import com.itheima.result.PageResult;
import com.itheima.result.Result;
import com.itheima.service.CategoryService;
import com.itheima.service.DishService;
import com.itheima.utils.AliOssUtil;
import com.itheima.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping
    public Result addDish(@RequestBody DishDTO dishDTO){
        dishService.addDish(dishDTO);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        String url = null;
        try {
            url = aliOssUtil.upload(file);
            return Result.success(url);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败：" + e.getMessage());
        }

    }

    @GetMapping("/page")
    public Result<PageResult<DishVO>> getDishList(DishQueryDTO dishQueryDTO){
        return Result.success(dishService.getDishList(dishQueryDTO));
    }
}
