package com.minutch.fox.biz.decoration.impl;

import java.math.BigDecimal;
import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.GoodsService;
import com.minutch.fox.param.decoration.GoodsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.dao.decoration.GoodsDao;
import com.minutch.fox.entity.decoration.Goods;


@Service
public class GoodsServiceImpl extends BaseServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public List<Goods> getAll() {
        return super.getAll(goodsDao);
    }

    public Goods getById(Long id) {
        return super.getById(goodsDao, id);
    }

    public boolean save(Goods goods) {
        return super.save(goodsDao, goods);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(goodsDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(goodsDao, ids);
    }

    @Override
    public List<Goods> queryGoods(GoodsQueryParam param) {
        return goodsDao.queryGoods(param, param.getStart(), param.getEnd());
    }

    @Override
    public int queryGoodsCount(GoodsQueryParam param) {
        return goodsDao.queryGoodsCount(param);
    }

    @Override
    public Goods queryByNameAndModel(String goodsName, String goodsModel, Long whId, Long storeId) {
        return goodsDao.queryByNameAndModel(goodsName, goodsModel, whId, storeId);
    }

    @Override
    public List<Goods> queryAllStoreGoods(Long storeId) {
        return goodsDao.queryAllStoreGoods(storeId);
    }

    @Override
    public int updateStockNum(Long goodsId, BigDecimal goodsNum) {
        return goodsDao.updateStockNum(goodsId, goodsNum);
    }

    @Override
    public int queryTotalCount(Long storeId) {
        return goodsDao.queryTotalCount(storeId);
    }

    @Override
    public List<Goods> queryStockNumAsc(Long storeId) {
        return goodsDao.queryStockNumAsc(storeId);
    }

    @Override
    public List<String> queryBaseGoodsName(Long storeId) {
        return goodsDao.queryBaseGoodsName(storeId);
    }

}
