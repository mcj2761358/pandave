package com.minutch.fox.dao.decoration;
import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.OrderHeader;
import com.minutch.fox.param.decoration.CustomerTotalAmountParam;
import com.minutch.fox.param.decoration.OrderHeaderQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface OrderHeaderDao extends BaseDao<OrderHeader> {

    List<OrderHeader> queryByCusId(@Param("storeId")Long storeId,@Param("cusId")Long cusId);

    int saveTotalAmount(@Param("param")CustomerTotalAmountParam param);

    List<OrderHeader> queryHeader(@Param("param") OrderHeaderQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryHeaderCount(@Param("param") OrderHeaderQueryParam param);

    OrderHeader queryByOrderSn(@Param("orderSn")String orderSn,@Param("cusId")Long cusId);
}
