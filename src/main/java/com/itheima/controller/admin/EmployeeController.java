package com.itheima.controller.admin;

import com.itheima.dto.EmployeeFixPwdDTO;
import com.itheima.dto.EmployeeRegisterDTO;
import com.itheima.entity.Employee;
import com.itheima.result.Result;
import com.itheima.service.EmployeeService;
import com.itheima.utils.JwtUtils;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public Result register(@Validated @RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        //看是否已存在
        Employee e = employeeService.findByUsername(employeeRegisterDTO.getUsername());
        if (e != null) {
            return Result.error("用户已注册");
        }
        if(!StringUtils.hasLength(employeeRegisterDTO.getUsername()) || !StringUtils.hasLength(employeeRegisterDTO.getPassword()) || !StringUtils.hasLength(employeeRegisterDTO.getConfirmPassword())){
            return Result.error("缺少参数");
        }
        if(!employeeRegisterDTO.getPassword().equals(employeeRegisterDTO.getConfirmPassword())){
            return Result.error("新密码与确认密码不一致");
        }
        employeeService.register(employeeRegisterDTO);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(EmployeeRegisterDTO employeeRegisterDTO) {
        Employee e = employeeService.findByUsername(employeeRegisterDTO.getUsername());
        if(e == null){
            return Result.error("该用户不存在");
        }
        if(!employeeRegisterDTO.getPassword().equals(e.getPassword())){
            return Result.error("密码错误");
        }
        String token = JwtUtils.generateToken(e.getId(), e.getUsername(),e.getUpdateTime().toString());

        return Result.success(token);
    }

    @GetMapping("/employeeInfo")
    public Result<Employee> employeeInfo(@RequestHeader(name = "Authorization") String token) {
        Map<String, Object> map = JwtUtils.parseToken(token);
        String username = (String) map.get("username");

        Employee employee = employeeService.findByUsername(username);
        return Result.success(employee);
    }

    @PutMapping("/fixpwd")
    public Result fixpwd(@RequestBody @Validated EmployeeFixPwdDTO employeeFixPwdDTO) {
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Employee emp = employeeService.findByUsername(username);

        if(!emp.getPassword().equals(employeeFixPwdDTO.getOldPwd())){
            return Result.error("密码不正确");
        }

        employeeService.fixpwd(username,employeeFixPwdDTO.getNewPwd());
        return Result.success();

    }
}
