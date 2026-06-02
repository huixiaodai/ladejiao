package com.itheima.controller.admin;

import com.itheima.dto.CategoryAddDTO;
import com.itheima.dto.CategoryQueryDTO;
import com.itheima.entity.Category;
import com.itheima.result.PageResult;
import com.itheima.result.Result;
import com.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addCategory(@RequestBody CategoryAddDTO categoryAddDTO) {
        categoryService.add(categoryAddDTO);
        return Result.success();
    }

    //分类分页查询
    @GetMapping("/page")
    public Result<PageResult<Category>> getlist(CategoryQueryDTO categoryQueryDTO){
        return Result.success(categoryService.getlist(categoryQueryDTO));
    }

    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Integer id) {
        return Result.success(categoryService.getById(id));
    }

    @PutMapping("/status/{id}")
    public Result updateStatus(@PathVariable Integer id) {
        categoryService.updateStatus(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated CategoryAddDTO categoryAddDTO){
        categoryService.update(categoryAddDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Category>> getListAll(Integer type){
        List<Category> categoryList = categoryService.getListAll(type);
        return Result.success(categoryList);
    }
}
