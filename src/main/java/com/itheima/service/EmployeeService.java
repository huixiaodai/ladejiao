package com.itheima.service;

import com.itheima.dto.EmployeeDTO;
import com.itheima.dto.EmployeePageQureyDTO;
import com.itheima.dto.EmployeeRegisterDTO;
import com.itheima.entity.Employee;
import com.itheima.result.PageResult;

public interface EmployeeService {
    void register(EmployeeRegisterDTO employeeRegisterDTO);

    Employee findByUsername(String username);

    void fixpwd(String username,String password);


    void add(EmployeeDTO employeeDTO);


    PageResult<Employee> getlist(EmployeePageQureyDTO employeePageQureyDTO);

    Employee getById(Integer id);


    void update(EmployeeDTO employeeDTO);

    void updateStatus(Integer id);

    void deleteById(Integer id);
}
