package com.minutch.fox.biz.dance.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.dance.EmployeeService;
import com.minutch.fox.dao.dance.EmployeeDao;
import com.minutch.fox.entity.dance.Employee;


@Service
public class EmployeeServiceImpl extends BaseServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    public List<Employee> getAll() {
        return super.getAll(employeeDao);
    }

    public Employee getById(Long id) {
        return super.getById(employeeDao, id);
    }

    public boolean save(Employee employee) {
        return super.save(employeeDao, employee);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(employeeDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(employeeDao, ids);
    }

    @Override
    public Employee queryByMobile(String mobilephone) {
        return employeeDao.queryByMobile(mobilephone);
    }
}
