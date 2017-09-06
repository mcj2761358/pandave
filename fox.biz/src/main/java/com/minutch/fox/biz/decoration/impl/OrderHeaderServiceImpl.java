package com.minutch.fox.biz.decoration.impl;

import java.math.BigDecimal;
import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.OrderHeaderService;
import com.minutch.fox.param.decoration.order.CustomerTotalAmountParam;
import com.minutch.fox.param.decoration.OrderHeaderQueryParam;
import com.minutch.fox.param.decoration.order.OrderSaleParam;
import com.minutch.fox.param.decoration.order.DashboardOrderHeaderParam;
import com.minutch.fox.view.decoration.OrderHeaderAmountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.dao.decoration.OrderHeaderDao;
import com.minutch.fox.entity.decoration.OrderHeader;


@Service
public class OrderHeaderServiceImpl extends BaseServiceImpl implements OrderHeaderService {

    @Autowired
    private OrderHeaderDao orderHeaderDao;

    public List<OrderHeader> getAll() {
        return super.getAll(orderHeaderDao);
    }

    public OrderHeader getById(Long id) {
        return super.getById(orderHeaderDao, id);
    }

    public boolean save(OrderHeader orderHeader) {
        return super.save(orderHeaderDao, orderHeader);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(orderHeaderDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(orderHeaderDao, ids);
    }

    @Override
    public List<OrderHeader> queryByCusId(Long storeId, Long cusId) {
        return orderHeaderDao.queryByCusId(storeId, cusId);
    }


    @Override
    public int saveTotalAmount(CustomerTotalAmountParam param) {
        return orderHeaderDao.saveTotalAmount(param);
    }

    @Override
    public List<OrderHeader> queryHeader(OrderHeaderQueryParam param) {
        return orderHeaderDao.queryHeader(param, param.getStart(), param.getEnd());
    }

    @Override
    public int queryHeaderCount(OrderHeaderQueryParam param) {
        return orderHeaderDao.queryHeaderCount(param);
    }

    @Override
    public OrderHeader queryByOrderSn(String orderSn, Long cusId) {
        return orderHeaderDao.queryByOrderSn(orderSn, cusId);
    }

    @Override
    public int queryTotalCount(Long storeId) {
        return orderHeaderDao.queryTotalCount(storeId);
    }

    @Override
    public int updateEmpName(OrderSaleParam param) {
        return orderHeaderDao.updateEmpName(param);
    }

    @Override
    public BigDecimal queryTotalAmountByTime(DashboardOrderHeaderParam param) {
        return orderHeaderDao.queryTotalAmountByTime(param);
    }

    @Override
    public BigDecimal queryInTotalAmountByTime(DashboardOrderHeaderParam param) {
        return orderHeaderDao.queryInTotalAmountByTime(param);
    }

    @Override
    public int queryTotalNumByTime(DashboardOrderHeaderParam param) {
        return orderHeaderDao.queryTotalNumByTime(param);
    }

    @Override
    public List<OrderHeaderAmountView> reportTotalAmount(Long storeId,String fromTime) {
        return orderHeaderDao.reportTotalAmount(storeId, fromTime);
    }
}
