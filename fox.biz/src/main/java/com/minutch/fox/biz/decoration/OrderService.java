package com.minutch.fox.biz.decoration;

import com.minutch.fox.entity.decoration.Order;

import java.util.List;

public interface OrderService {
  List<Order> getAll();
  
  Order getById(Long id);

  boolean save(Order order);

  boolean deleteById( Long id);

   int deleteByIds(Long[] ids);

}
