package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.decoration.WarehouseService;
import com.minutch.fox.dao.decoration.WarehouseDao;
import com.minutch.fox.entity.decoration.Warehouse;


@Service
public class WarehouseServiceImpl extends BaseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;

    public List<Warehouse> getAll() {
        return super.getAll(warehouseDao);
    }

    public Warehouse getById(Long id) {
        return super.getById(warehouseDao, id);
    }

    public boolean save(Warehouse warehouse) {
        return super.save(warehouseDao, warehouse);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(warehouseDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(warehouseDao, ids);
    }

    @Override
    public List<Warehouse> queryAllWarehouse(Long storeId) {
        return warehouseDao.queryAllWarehouse(storeId);
    }
}
