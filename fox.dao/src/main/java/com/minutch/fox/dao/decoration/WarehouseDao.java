package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Warehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface WarehouseDao extends BaseDao<Warehouse> {

    List<Warehouse> queryAllWarehouse(@Param("storeId")Long storeId);
}
