package com.itheima.mapper;

import com.itheima.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Insert("Insert Into employee (name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "values (#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},now(),now(),#{createUser},#{updateUser})")
    void register(Employee employee);

    @Select("select * from employee where username = #{username}")
    Employee findByUsername(String username);

    @Update("update employee set password = #{password},update_time = now() where username = #{username}")
    void fixpwd(String username,String password);

    @Insert("INSERT INTO employee(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user,status) " +
            "values "+
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})"
    )
    void add(Employee employee);

    List<Employee> getlist(String name);
}
