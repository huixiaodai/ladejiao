package com.itheima.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAddDTO implements Serializable {
    @NotNull(groups = Update.class)
    private Integer id;
    private String name;
    private Integer type;
    private Integer sort;

    public interface Add extends Default{}
    public interface Update extends Default{}
}
