package com.minutch.fox.biz.decoration;

import java.util.List; 

import com.minutch.fox.entity.decoration.Customer;

public interface CustomerService {
  List<Customer> getAll();
  
  Customer getById(Long id);

  boolean save(Customer customer);

  boolean deleteById( Long id);

   int deleteByIds(Long[] ids);

}
