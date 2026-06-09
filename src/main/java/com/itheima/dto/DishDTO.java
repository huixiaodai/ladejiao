package com.itheima.dto;

import com.itheima.entity.DishFlavor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO implements Serializable {
    @NotNull(groups = Update.class)
    private Integer id;
    //菜品名称
    private String name;
    //菜品分类id
    private Integer categoryId;
    //菜品价格
    private BigDecimal price;
    //图片
    private String image;
    //描述信息
    private String description;
    //0 停售 1 起售
    private Integer status;
    // 多种口味，包括温度，忌口等(每种口味又对应一个列表)，且数据在口味表中而不是在Dish里，口味表有外键关联Dish
    private List<DishFlavorDTO> flavors = new ArrayList<>();

    public interface Add extends Default{};
    public interface Update extends Default{};
}
