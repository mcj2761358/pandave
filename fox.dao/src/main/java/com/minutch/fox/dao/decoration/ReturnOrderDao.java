package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.ReturnOrder;
import com.minutch.fox.param.decoration.ReturnOrderQueryParam;
import com.minutch.fox.view.decoration.OrderView;
import com.minutch.fox.view.decoration.ReturnOrderView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface ReturnOrderDao extends BaseDao<ReturnOrder> {

    List<ReturnOrderView> queryReturnOrder(@Param("param") ReturnOrderQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryReturnOrderCount(@Param("param") ReturnOrderQueryParam param);

    List<ReturnOrderView> queryByHeaderId(@Param("headerId")Long headerId);
}
