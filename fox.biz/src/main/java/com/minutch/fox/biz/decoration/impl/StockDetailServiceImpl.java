package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.decoration.StockDetailService;
import com.minutch.fox.dao.decoration.StockDetailDao;
import com.minutch.fox.entity.decoration.StockDetail;


@Service
public class StockDetailServiceImpl extends BaseServiceImpl implements StockDetailService {

    @Autowired
    private StockDetailDao stockDetailDao;

    public List<StockDetail> getAll() {
        return super.getAll(stockDetailDao);
    }

    public StockDetail getById(Long id) {
        return super.getById(stockDetailDao, id);
    }

    public boolean save(StockDetail stockDetail) {
        return super.save(stockDetailDao, stockDetail);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(stockDetailDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(stockDetailDao, ids);
    }

    @Override
    public List<StockDetail> queryByGoodsId(Long goodsId) {
        return stockDetailDao.queryByGoodsId(goodsId);
    }
}
