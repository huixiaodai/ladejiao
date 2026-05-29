package com.itheima.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    @NotNull(message = "id不能为空",groups = Update.class)
    private Integer id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

    public interface Add extends Default{}
    public interface Update extends Default{}

}
