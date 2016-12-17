package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.dao.decoration.CustomerDao;
import com.minutch.fox.entity.decoration.Customer;


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
}
