package com.itheima.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePageQureyDTO {

    @NotNull(message = "page不能为空")
    private Integer page;

    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

    private String name;
}
