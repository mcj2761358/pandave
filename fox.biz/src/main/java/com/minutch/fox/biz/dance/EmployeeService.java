package com.minutch.fox.biz.dance;

import com.minutch.fox.entity.dance.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();

    Employee getById(Long id);

    boolean save(Employee employee);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    Employee queryByMobile(String mobilephone);
}
