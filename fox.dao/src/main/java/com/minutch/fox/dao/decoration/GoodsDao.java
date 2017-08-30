package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Goods;
import com.minutch.fox.param.decoration.GoodsQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface GoodsDao extends BaseDao<Goods> {

    List<Goods> queryGoods(@Param("param") GoodsQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryGoodsCount(@Param("param") GoodsQueryParam param);

    Goods queryByNameAndModel(@Param("goodsName")String goodsName,@Param("goodsModel")String goodsModel,@Param("whId")Long whId,@Param("storeId")Long storeId);

    List<Goods> queryAllStoreGoods(@Param("storeId")Long storeId);

    int updateStockNum(@Param("goodsId")Long goodsId,@Param("goodsNum")int goodsNum);

    int queryTotalCount(@Param("storeId")Long storeId);
}
