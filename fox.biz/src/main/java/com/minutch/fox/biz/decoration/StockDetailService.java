package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.StockDetail;

public interface StockDetailService {
    List<StockDetail> getAll();

    StockDetail getById(Long id);

    boolean save(StockDetail stockDetail);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    List<StockDetail> queryByGoodsId(Long goodsId);
}
