package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.ReturnOrder;

public interface ReturnOrderService {
  public List<ReturnOrder> getAll();
  
  public ReturnOrder getById(Long id);

  public boolean save(ReturnOrder returnOrder);

  public boolean deleteById( Long id);

  public  int deleteByIds(Long[] ids);

}
