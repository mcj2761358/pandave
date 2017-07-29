package com.minutch.fox.biz.decoration.impl;


import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.StoreService;
import com.minutch.fox.dao.decoration.StoreDao;
import com.minutch.fox.entity.decoration.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
}
