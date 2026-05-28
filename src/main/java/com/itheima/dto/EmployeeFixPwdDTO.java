package com.itheima.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFixPwdDTO implements Serializable {
    private String oldPwd;
    @Pattern(regexp = "^\\S{5,10}$",message = "密码长度必须是5到10位")
    private String newPwd;
}
