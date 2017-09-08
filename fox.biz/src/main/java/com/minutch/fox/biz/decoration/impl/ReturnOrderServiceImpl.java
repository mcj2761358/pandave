package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.param.decoration.ReturnOrderQueryParam;
import com.minutch.fox.view.decoration.ReturnOrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.decoration.ReturnOrderService;
import com.minutch.fox.dao.decoration.ReturnOrderDao;
import com.minutch.fox.entity.decoration.ReturnOrder;


@Service
public class ReturnOrderServiceImpl extends BaseServiceImpl implements ReturnOrderService{

  @Autowired
  private ReturnOrderDao returnOrderDao;

  public List<ReturnOrder> getAll() {
    return super.getAll(returnOrderDao);
  }

  public ReturnOrder getById(Long id) {
    return super.getById(returnOrderDao, id);
  }

  public boolean save(ReturnOrder returnOrder) {
    return super.save(returnOrderDao, returnOrder);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(returnOrderDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(returnOrderDao, ids);
  }

  @Override
  public List<ReturnOrderView> queryReturnOrder(ReturnOrderQueryParam param) {
    return returnOrderDao.queryReturnOrder(param, param.getStart(), param.getEnd());
  }

  @Override
  public int queryReturnOrderCount(ReturnOrderQueryParam param) {
    return returnOrderDao.queryReturnOrderCount(param);
  }

  @Override
  public List<ReturnOrderView> queryByHeaderId(Long headerId) {
    return returnOrderDao.queryByHeaderId(headerId);
  }
}
