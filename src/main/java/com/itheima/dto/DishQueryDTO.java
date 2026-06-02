package com.itheima.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "page不能为空")
    private Integer page;

    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

    private String name;
    private Integer categoryId;
    private Integer status;
}
