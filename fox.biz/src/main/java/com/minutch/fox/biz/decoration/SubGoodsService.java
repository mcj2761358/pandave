package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.Goods;
import com.minutch.fox.entity.decoration.SubGoods;
import com.minutch.fox.view.decoration.SubGoodsView;

public interface SubGoodsService {
    List<SubGoods> getAll();

    SubGoods getById(Long id);

    boolean save(SubGoods subGoods);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    SubGoods queryByGoodsIdAndSubGoodsId(Long goodsId,Long subGoodsId);

    List<SubGoodsView> queryViewByGoodsId(Long goodsId);

    List<SubGoods> queryByGoodsId(Long goodsId);
}
