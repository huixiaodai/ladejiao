package com.itheima.vo;

import com.itheima.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishVO {
    private Integer id;
    private String name;
    private String image;
    private String description;
    private BigDecimal price;
    private Integer status;
    private Integer categoryId;
    private LocalDateTime updateTime;
    private List<DishFlavor> flavors;
    private String categoryName;
}
