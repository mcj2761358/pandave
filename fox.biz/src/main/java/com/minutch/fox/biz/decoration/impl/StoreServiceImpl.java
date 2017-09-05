package com.minutch.fox.biz.decoration.impl;


import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.StoreService;
import com.minutch.fox.dao.decoration.StoreDao;
import com.minutch.fox.entity.decoration.Store;
import com.minutch.fox.enu.decoration.StoreLevelEnum;
import com.minutch.fox.param.decoration.StoreQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StoreServiceImpl extends BaseServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;

    public List<Store> getAll() {
        return super.getAll(storeDao);
    }

    public Store getById(Long id) {
        return super.getById(storeDao, id);
    }

    public boolean save(Store store) {
        return super.save(storeDao, store);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(storeDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(storeDao, ids);
    }

    @Override
    public Store queryByMobilePhone(String mobilePhone) {
        return storeDao.queryByMobilePhone(mobilePhone);
    }

    @Override
    public int updatePassword(Long id, String password) {
        return storeDao.updatePassword(id, password);
    }

    @Override
    public StoreLevelEnum queryStoreLevel(Long storeId) {

        StoreLevelEnum level = StoreLevelEnum.Free;

        Store store = storeDao.selectById(storeId);
        if (store != null && StringUtils.isNotBlank(store.getStoreLevel())) {
            try {
                level = StoreLevelEnum.valueOf(store.getStoreLevel());
            } catch (Exception e) {
                log.error("店铺年费等级["+store.getStoreLevel()+"]有误.", e);
            }
        }

        return level;
    }

    @Override
    public List<Store> queryStore(StoreQueryParam param) {
        return storeDao.queryStore(param, param.getStart(), param.getEnd());
    }

    @Override
    public int queryStoreCount(StoreQueryParam param) {
        return storeDao.queryStoreCount(param);
    }

    @Override
    public boolean isAdmin(Long storeId) {

        Store store = storeDao.selectById(storeId);
        if (store == null ) {
            return false;
        }

        if ("admin".equals(store.getBeAdmin())) {
            return true;
        }
        return false;
    }
}
