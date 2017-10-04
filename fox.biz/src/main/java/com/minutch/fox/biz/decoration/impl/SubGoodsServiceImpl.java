package com.minutch.fox.biz.decoration.impl;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.SubGoodsService;
import com.minutch.fox.dao.decoration.SubGoodsDao;
import com.minutch.fox.entity.decoration.Goods;
import com.minutch.fox.entity.decoration.SubGoods;
import com.minutch.fox.view.decoration.SubGoodsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubGoodsServiceImpl extends BaseServiceImpl implements SubGoodsService {

    @Autowired
    private SubGoodsDao subGoodsDao;

    public List<SubGoods> getAll() {
        return super.getAll(subGoodsDao);
    }

    public SubGoods getById(Long id) {
        return super.getById(subGoodsDao, id);
    }

    public boolean save(SubGoods subGoods) {
        return super.save(subGoodsDao, subGoods);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(subGoodsDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(subGoodsDao, ids);
    }

    @Override
    public SubGoods queryByGoodsIdAndSubGoodsId(Long goodsId, Long subGoodsId) {
        return subGoodsDao.queryByGoodsIdAndSubGoodsId(goodsId, subGoodsId);
    }

    @Override
    public List<SubGoodsView> queryViewByGoodsId(Long goodsId) {
        return subGoodsDao.queryViewByGoodsId(goodsId);
    }

    @Override
    public List<SubGoods> queryByGoodsId(Long goodsId) {
        return subGoodsDao.queryByGoodsId(goodsId);
    }
}
