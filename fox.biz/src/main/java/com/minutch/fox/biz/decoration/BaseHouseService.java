package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.BaseHouse;

public interface BaseHouseService {
    List<BaseHouse> getAll();

    BaseHouse getById(Long id);

    boolean save(BaseHouse baseHouse);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

}
