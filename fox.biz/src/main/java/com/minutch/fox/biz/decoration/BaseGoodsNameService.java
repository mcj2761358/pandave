package com.minutch.fox.biz.decoration;

import com.minutch.fox.entity.decoration.BaseGoodsName;

import java.util.List;

public interface BaseGoodsNameService {

    List<BaseGoodsName> getAll();

    BaseGoodsName getById(Long id);

    boolean save(BaseGoodsName baseGoodsName);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

}
