package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Employee;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface EmployeeDao extends BaseDao<Employee> {

    Employee queryByMobilePhone(@Param("empMobile")String empMobile);

    int updatePassword(@Param("id")Long id,@Param("password")String password);
}
