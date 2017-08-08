package com.minutch.fox.biz.decoration;

import com.minutch.fox.entity.decoration.Customer;
import com.minutch.fox.param.decoration.CustomerQueryParam;
import com.minutch.fox.param.decoration.CustomerTotalAmountParam;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();

    Customer getById(Long id);

    boolean save(Customer customer);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    List<Customer> queryCustomer(CustomerQueryParam param);

    int queryCustomerCount(CustomerQueryParam param);

    Customer queryByMobilePhone(String mobilePhone);

    int saveTotalAmount(CustomerTotalAmountParam param);
}
