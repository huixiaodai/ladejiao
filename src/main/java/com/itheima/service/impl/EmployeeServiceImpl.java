package com.itheima.service.impl;

import com.itheima.dto.EmployeeRegisterDTO;
import com.itheima.entity.Employee;
import com.itheima.mapper.EmployeeMapper;
import com.itheima.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void register(EmployeeRegisterDTO employeeRegisterDTO) {
        Employee employee = new Employee();
        // 将employeeRegisterDTO属性拷贝到employee中
        BeanUtils.copyProperties(employeeRegisterDTO, employee);

        employee.setName("员工");
        employee.setStatus(1);
        employee.setSex("女");
        employee.setPhone("11111111111");
        employee.setIdNumber("110101199001010048");
        employee.setCreateUser(100); // 100表示员工自己注册，此时还不能拿到BaseContext的currentId，只能用100这个数字表示自己了
        employee.setUpdateUser(100);
        employeeMapper.register(employee);
    }

    @Override
    public Employee findByUsername(String username) {
        return employeeMapper.findByUsername(username);
    }

    @Override
    public void fixpwd(String username,String password) {
        employeeMapper.fixpwd(username,password);
    }
}
