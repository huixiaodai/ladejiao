package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dto.EmployeePageQureyDTO;
import com.itheima.dto.EmployeeRegisterDTO;
import com.itheima.entity.Employee;
import com.itheima.mapper.EmployeeMapper;
import com.itheima.result.PageResult;
import com.itheima.service.EmployeeService;
import com.itheima.utils.ThreadLocalUtil;
import com.itheima.dto.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Override
    public void add(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置默认密码
        employee.setPassword("123456");
        employee.setStatus(1);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //设置当前记录创建人id和修改人id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        employee.setCreateUser(id);
        employee.setUpdateUser(id);

        employeeMapper.add(employee);
    }

    @Override
    public PageResult<Employee> getlist(EmployeePageQureyDTO employeePageQureyDTO) {

        //1.开启pageHelper
        PageHelper.startPage(employeePageQureyDTO.getPage(),employeePageQureyDTO.getPageSize());

        //2.查询数据
        List<Employee> list = employeeMapper.getlist(employeePageQureyDTO.getName());

        //3.将list转为page
        Page<Employee> page = (Page<Employee>) list;

        //4.新建一个pageResult
        PageResult<Employee> pageResult = new PageResult<>();

        //5.将page里面的total和Records放进去
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());

        return pageResult;
    }

    @Override
    public Employee getById(Integer id) {
        return employeeMapper.getById(id);
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        //1.new Employee
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        employee.setUpdateUser(id);

        employee.setUpdateTime(LocalDateTime.now());

        employeeMapper.update(employee);
    }

    @Override
    public void updateStatus(Integer id) {
        Employee e = employeeMapper.findById(id);

        if (e == null) {
            throw new RuntimeException("员工不存在");
        }

        Integer status = e.getStatus();
        if (status == 1) {
            e.setStatus(0);
        }
        else{
            e.setStatus(1);
        }
        //e.setStatus(e.getStatus() == 1 ? 0 : 1);
        employeeMapper.update(e);
    }

    @Override
    public void deleteById(Integer id) {
        employeeMapper.deleteById(id);
    }
}
