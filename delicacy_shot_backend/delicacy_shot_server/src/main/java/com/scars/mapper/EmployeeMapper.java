package com.scars.mapper;

import com.github.pagehelper.Page;
import com.scars.dto.EmployeePageQueryDTO;
import com.scars.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     *
     * @param employee
     */
    @Insert("insert into employee (id, name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "values " +
            "(#{id}, #{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    /**
     * 员工信息分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用和禁用员工账号
     * @param employee
     */
    void update(Employee employee);
}
