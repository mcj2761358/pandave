package com.minutch.pandave.biz.dance;

import com.minutch.pandave.entity.dance.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAll();

    public Employee getById(Long id);

    public boolean save(Employee employee);

    public boolean deleteById(Long id);

    public int deleteByIds(Long[] ids);

    public Employee queryByMobile(String mobilephone);
}
