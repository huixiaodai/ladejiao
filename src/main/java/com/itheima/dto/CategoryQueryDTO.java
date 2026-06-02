package com.itheima.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryQueryDTO implements Serializable {

    private Integer page;
    private Integer pageSize;
    private String name;
    private Integer type;
}
