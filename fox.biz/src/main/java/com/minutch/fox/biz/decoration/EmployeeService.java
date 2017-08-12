package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.Employee;

public interface EmployeeService {
    List<Employee> getAll();

    Employee getById(Long id);

    boolean save(Employee employee);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    Employee queryByMobilePhone(String empMobile);

    int updatePassword(Long id, String password);
}
