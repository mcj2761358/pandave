package com.minutch.pandave.dao.dance;

import com.minutch.pandave.dao.base.BaseDao;
import com.minutch.pandave.dao.base.MyBatisRepository;
import com.minutch.pandave.entity.dance.Employee;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface EmployeeDao extends BaseDao<Employee> {

    public Employee queryByMobile(@Param("mobilephone")String mobilephone);
}
