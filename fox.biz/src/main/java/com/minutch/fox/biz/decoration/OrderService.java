package com.minutch.fox.biz.decoration;

import com.minutch.fox.entity.decoration.Order;
import com.minutch.fox.param.decoration.OrderQueryParam;
import com.minutch.fox.param.decoration.order.DashboardOrderGoodsParam;
import com.minutch.fox.view.decoration.OrderGoodsView;
import com.minutch.fox.view.decoration.OrderView;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> getAll();

    Order getById(Long id);

    boolean save(Order order);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    List<OrderView> queryOrder(OrderQueryParam param);

    int queryOrderCount(OrderQueryParam param);

    int finishById(Long orderId);

    int handleRemindById(Long orderId);

    List<Order> queryByIds(List<Long> idList);

    List<Order> queryByHeaderId(Long headerId);

    int handleReturnOrder(Long orderId,int goodsNum,BigDecimal orderAmount);

    void handOrderQueryParam(OrderQueryParam param);

    List<OrderGoodsView> queryOrderGoodsNumByTime(DashboardOrderGoodsParam param);
}
