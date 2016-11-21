package com.minutch.fox.dao.dance;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.dance.Employee;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface EmployeeDao extends BaseDao<Employee> {

    public Employee queryByMobile(@Param("mobilephone")String mobilephone);
}
