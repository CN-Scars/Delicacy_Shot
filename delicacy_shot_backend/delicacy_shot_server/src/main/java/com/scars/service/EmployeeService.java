package com.scars.service;

import com.scars.dto.EmployeeDTO;
import com.scars.dto.EmployeeLoginDTO;
import com.scars.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);
}
