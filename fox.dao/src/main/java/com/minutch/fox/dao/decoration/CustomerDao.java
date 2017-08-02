package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Customer;
import com.minutch.fox.param.decoration.CustomerQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface CustomerDao extends BaseDao<Customer> {

    List<Customer> queryCustomer(@Param("param") CustomerQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryCustomerCount(@Param("param") CustomerQueryParam param);

    Customer queryByMobilePhone(@Param("mobilePhone")String mobilePhone);
}
