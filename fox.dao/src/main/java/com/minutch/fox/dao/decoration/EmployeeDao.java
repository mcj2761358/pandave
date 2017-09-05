package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Employee;
import com.minutch.fox.param.decoration.EmployeeQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface EmployeeDao extends BaseDao<Employee> {


    List<Employee> queryEmployee(@Param("param") EmployeeQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryEmployeeCount(@Param("param") EmployeeQueryParam param);


    Employee queryByMobilePhone(@Param("empMobile")String empMobile);

    Employee queryMainEmp(@Param("storeId")Long storeId);

    List<Employee> queryMainEmps(@Param("storeIdList")List<Long> storeIdList);

    int updatePassword(@Param("id")Long id,@Param("password")String password);


    int queryTotalCount(@Param("storeId")Long storeId);

    List<Employee> queryAllStoreEmp(@Param("storeId")Long storeId);


}
