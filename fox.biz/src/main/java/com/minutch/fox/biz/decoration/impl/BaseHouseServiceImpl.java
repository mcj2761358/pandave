package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.decoration.BaseHouseService;
import com.minutch.fox.dao.decoration.BaseHouseDao;
import com.minutch.fox.entity.decoration.BaseHouse;


@Service
public class BaseHouseServiceImpl extends BaseServiceImpl implements BaseHouseService{

  @Autowired
  private BaseHouseDao baseHouseDao;

  public List<BaseHouse> getAll() {
    return super.getAll(baseHouseDao);
  }

  public BaseHouse getById(Long id) {
    return super.getById(baseHouseDao, id);
  }

  public boolean save(BaseHouse baseHouse) {
    return super.save(baseHouseDao, baseHouse);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(baseHouseDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(baseHouseDao, ids);
  }
}
