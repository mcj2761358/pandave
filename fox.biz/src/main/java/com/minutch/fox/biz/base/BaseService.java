package com.minutch.fox.biz.base;


import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.entity.base.IdEntity;

import java.util.List;

public interface BaseService {

    <T extends IdEntity> List<T> getAll(BaseDao<T> baseDao);

    <T extends IdEntity> T getById(BaseDao<T> baseDao, Object id);

    <T extends IdEntity> boolean save(BaseDao<T> baseDao, T entity);

    <T extends IdEntity> boolean deleteById(BaseDao<T> baseDao, Object id);

    <T extends IdEntity> int deleteByIds(BaseDao<T> baseDao, Object[] ids);

}
