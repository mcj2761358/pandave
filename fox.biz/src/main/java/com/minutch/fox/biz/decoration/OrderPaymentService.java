package com.minutch.fox.biz.decoration;

import com.minutch.fox.entity.decoration.OrderPayment;

import java.util.List;

public interface OrderPaymentService {
  List<OrderPayment> getAll();
  
  OrderPayment getById(Long id);

  boolean save(OrderPayment orderPayment);

  boolean deleteById( Long id);

   int deleteByIds(Long[] ids);

}
