package com.minutch.fox.biz.decoration;

import com.minutch.fox.entity.decoration.OrderHeader;
import com.minutch.fox.param.decoration.order.CustomerTotalAmountParam;
import com.minutch.fox.param.decoration.OrderHeaderQueryParam;
import com.minutch.fox.param.decoration.order.OrderSaleParam;
import com.minutch.fox.param.decoration.order.DashboardOrderHeaderParam;

import java.math.BigDecimal;
import java.util.List;

public interface OrderHeaderService {
    List<OrderHeader> getAll();

    OrderHeader getById(Long id);

    boolean save(OrderHeader orderHeader);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    List<OrderHeader> queryByCusId(Long storeId,Long cusId);

    int saveTotalAmount(CustomerTotalAmountParam param);

    List<OrderHeader> queryHeader(OrderHeaderQueryParam param);

    int queryHeaderCount(OrderHeaderQueryParam param);

    OrderHeader queryByOrderSn(String orderSn,Long cusId);

    int queryTotalCount(Long storeId);

    int updateEmpName(OrderSaleParam param);

    BigDecimal queryTotalAmountByTime(DashboardOrderHeaderParam param);

    int queryTotalNumByTime(DashboardOrderHeaderParam param);
}
