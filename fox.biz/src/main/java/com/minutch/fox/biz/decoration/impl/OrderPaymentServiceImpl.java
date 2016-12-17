package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.dao.decoration.OrderPaymentDao;
import com.minutch.fox.entity.decoration.OrderPayment;


@Service
public class OrderPaymentServiceImpl extends BaseServiceImpl implements OrderPaymentService {

  @Autowired
  private OrderPaymentDao orderPaymentDao;

  public List<OrderPayment> getAll() {
    return super.getAll(orderPaymentDao);
  }

  public OrderPayment getById(Long id) {
    return super.getById(orderPaymentDao, id);
  }

  public boolean save(OrderPayment orderPayment) {
    return super.save(orderPaymentDao, orderPayment);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(orderPaymentDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(orderPaymentDao, ids);
  }
}
