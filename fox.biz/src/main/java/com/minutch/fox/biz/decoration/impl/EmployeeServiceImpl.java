package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.decoration.EmployeeService;
import com.minutch.fox.dao.decoration.EmployeeDao;
import com.minutch.fox.entity.decoration.Employee;


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
    public Employee queryByMobilePhone(String empMobile) {
        return employeeDao.queryByMobilePhone(empMobile);
    }

    @Override
    public int updatePassword(Long id, String password) {
        return employeeDao.updatePassword(id, password);
    }
}
