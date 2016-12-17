package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.dao.decoration.OrderDao;
import com.minutch.fox.entity.decoration.Order;


@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

  @Autowired
  private OrderDao orderDao;

  public List<Order> getAll() {
    return super.getAll(orderDao);
  }

  public Order getById(Long id) {
    return super.getById(orderDao, id);
  }

  public boolean save(Order order) {
    return super.save(orderDao, order);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(orderDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(orderDao, ids);
  }
}
