package com.scars.service;

import com.scars.dto.EmployeeDTO;
import com.scars.dto.EmployeeLoginDTO;
import com.scars.dto.EmployeePageQueryDTO;
import com.scars.entity.Employee;
import com.scars.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 员工信息分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用和禁用员工账号
     * @param status
     * @param id
     * @return
     */
    void enableOrDisable(Integer status, long id);
}
