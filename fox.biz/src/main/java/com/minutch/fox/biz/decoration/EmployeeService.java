package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.Employee;
import com.minutch.fox.param.decoration.EmployeeQueryParam;

public interface EmployeeService {
    List<Employee> getAll();

    Employee getById(Long id);

    boolean save(Employee employee);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    Employee queryByMobilePhone(String empMobile);

    int updatePassword(Long id, String password);

    List<Employee> queryEmployee(EmployeeQueryParam param);

    int queryEmployeeCount(EmployeeQueryParam param);

    int queryTotalCount(Long storeId);

    List<Employee> queryAllStoreEmp(Long storeId);
}
