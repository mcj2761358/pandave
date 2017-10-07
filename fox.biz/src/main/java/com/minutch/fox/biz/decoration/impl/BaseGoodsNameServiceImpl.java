package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.decoration.BaseGoodsNameService;
import com.minutch.fox.dao.decoration.BaseGoodsNameDao;
import com.minutch.fox.entity.decoration.BaseGoodsName;


@Service
public class BaseGoodsNameServiceImpl extends BaseServiceImpl implements BaseGoodsNameService{

  @Autowired
  private BaseGoodsNameDao baseGoodsNameDao;

  public List<BaseGoodsName> getAll() {
    return super.getAll(baseGoodsNameDao);
  }

  public BaseGoodsName getById(Long id) {
    return super.getById(baseGoodsNameDao, id);
  }

  public boolean save(BaseGoodsName baseGoodsName) {
    return super.save(baseGoodsNameDao, baseGoodsName);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(baseGoodsNameDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(baseGoodsNameDao, ids);
  }
}
