package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Goods;
import com.minutch.fox.entity.decoration.SubGoods;
import com.minutch.fox.view.decoration.SubGoodsView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface SubGoodsDao extends BaseDao<SubGoods> {

    SubGoods queryByGoodsIdAndSubGoodsId(@Param("goodsId")Long goodsId,@Param("subGoodsId")Long subGoodsId);

    List<SubGoodsView> queryViewByGoodsId(@Param("goodsId")Long goodsId);

    List<SubGoods> queryByGoodsId(@Param("goodsId")Long goodsId);
}
