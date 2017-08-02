package com.minutch.fox.biz.decoration.impl;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.CustomerService;
import com.minutch.fox.dao.decoration.CustomerDao;
import com.minutch.fox.entity.decoration.Customer;
import com.minutch.fox.param.decoration.CustomerQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<Customer> getAll() {
        return super.getAll(customerDao);
    }

    public Customer getById(Long id) {
        return super.getById(customerDao, id);
    }

    public boolean save(Customer customer) {
        return super.save(customerDao, customer);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(customerDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(customerDao, ids);
    }

    @Override
    public List<Customer> queryCustomer(CustomerQueryParam param) {
        return customerDao.queryCustomer(param, param.getStart(), param.getEnd());
    }

    @Override
    public int queryCustomerCount(CustomerQueryParam param) {
        return customerDao.queryCustomerCount(param);
    }

    @Override
    public Customer queryByMobilePhone(String mobilePhone) {
        return customerDao.queryByMobilePhone(mobilePhone);
    }
}
