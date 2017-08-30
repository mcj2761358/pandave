package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.Warehouse;

public interface WarehouseService {
    List<Warehouse> getAll();

    Warehouse getById(Long id);

    boolean save(Warehouse warehouse);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    List<Warehouse> queryAllWarehouse(Long storeId);
}
