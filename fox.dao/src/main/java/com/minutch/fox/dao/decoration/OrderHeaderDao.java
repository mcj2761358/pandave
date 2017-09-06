package com.minutch.fox.dao.decoration;
import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.OrderHeader;
import com.minutch.fox.param.decoration.order.CustomerTotalAmountParam;
import com.minutch.fox.param.decoration.OrderHeaderQueryParam;
import com.minutch.fox.param.decoration.order.OrderSaleParam;
import com.minutch.fox.param.decoration.order.DashboardOrderHeaderParam;
import com.minutch.fox.view.decoration.OrderHeaderAmountView;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@MyBatisRepository
public interface OrderHeaderDao extends BaseDao<OrderHeader> {

    List<OrderHeader> queryByCusId(@Param("storeId")Long storeId,@Param("cusId")Long cusId);

    int saveTotalAmount(@Param("param")CustomerTotalAmountParam param);

    List<OrderHeader> queryHeader(@Param("param") OrderHeaderQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryHeaderCount(@Param("param") OrderHeaderQueryParam param);

    OrderHeader queryByOrderSn(@Param("orderSn")String orderSn,@Param("cusId")Long cusId);

    int queryTotalCount(@Param("storeId")Long storeId);

    int updateEmpName(@Param("param")OrderSaleParam param);

    BigDecimal queryTotalAmountByTime(@Param("param")DashboardOrderHeaderParam param);

    BigDecimal queryInTotalAmountByTime(@Param("param")DashboardOrderHeaderParam param);

    int queryTotalNumByTime(@Param("param")DashboardOrderHeaderParam param);

    List<OrderHeaderAmountView> reportTotalAmount(@Param("storeId")Long storeId, @Param("fromTime")String fromTime);
}
