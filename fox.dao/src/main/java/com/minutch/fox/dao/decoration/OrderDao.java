package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Order;
import com.minutch.fox.param.decoration.OrderQueryParam;
import com.minutch.fox.view.decoration.OrderView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface OrderDao extends BaseDao<Order> {

    List<OrderView> queryOrder(@Param("param") OrderQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryOrderCount(@Param("param") OrderQueryParam param);

    int finishById(@Param("orderId")Long orderId);

    int handleRemindById(@Param("orderId")Long orderId);

    List<Order> queryByIds(@Param("idList")List<Long> idList);

    List<Order> queryByHeaderId(@Param("headerId")Long headerId);
}
