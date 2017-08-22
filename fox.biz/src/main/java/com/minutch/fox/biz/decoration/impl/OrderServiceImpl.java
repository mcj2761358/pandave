package com.minutch.fox.biz.decoration.impl;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.OrderService;
import com.minutch.fox.dao.decoration.OrderDao;
import com.minutch.fox.entity.decoration.Order;
import com.minutch.fox.param.decoration.OrderQueryParam;
import com.minutch.fox.view.decoration.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public List<OrderView> queryOrder(OrderQueryParam param) {
        return orderDao.queryOrder(param, param.getStart(), param.getEnd());
    }

    @Override
    public int queryOrderCount(OrderQueryParam param) {
        return orderDao.queryOrderCount(param);
    }

    @Override
    public int finishById(Long orderId) {

        if (orderId == null) {
            return 0;
        }
        return orderDao.finishById(orderId);
    }

    @Override
    public int handleRemindById(Long orderId) {
        return orderDao.handleRemindById(orderId);
    }

    @Override
    public List<Order> queryByIds(List<Long> idList) {
        return orderDao.queryByIds(idList);
    }

    @Override
    public List<Order> queryByHeaderId(Long headerId) {
        return orderDao.queryByHeaderId(headerId);
    }
}
