package com.itheima.service;

import com.itheima.dto.EmployeeRegisterDTO;
import com.itheima.entity.Employee;

public interface EmployeeService {
    void register(EmployeeRegisterDTO employeeRegisterDTO);

    Employee findByUsername(String username);

    void fixpwd(String username,String password);
}
