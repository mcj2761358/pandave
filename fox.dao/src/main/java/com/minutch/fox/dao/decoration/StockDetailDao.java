package com.minutch.fox.dao.decoration;


import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.StockDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface StockDetailDao extends BaseDao<StockDetail> {

    List<StockDetail> queryByGoodsId(@Param("goodsId")Long goodsId);
}
