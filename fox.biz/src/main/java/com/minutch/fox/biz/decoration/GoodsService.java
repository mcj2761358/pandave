package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.Goods;
import com.minutch.fox.param.decoration.GoodsQueryParam;

public interface GoodsService {
    List<Goods> getAll();

    Goods getById(Long id);

    boolean save(Goods goods);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    List<Goods> queryGoods(GoodsQueryParam param);

    int queryGoodsCount(GoodsQueryParam param);

    Goods queryByNameAndModel(String goodsName, String goodsModel, Long whId, Long storeId);

    List<Goods> queryAllStoreGoods(Long storeId);

    int updateStockNum(Long goodsId,int goodsNum);

    int queryTotalCount(Long storeId);

    List<Goods> queryStockNumAsc(Long storeId);
}
