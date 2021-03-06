package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Order;
import com.minutch.fox.param.decoration.OrderQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface OrderDao extends BaseDao<Order> {

    List<Order> queryOrder(@Param("param") OrderQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryOrderCount(@Param("param") OrderQueryParam param);

    int finishById(@Param("orderId")Long orderId);
}
