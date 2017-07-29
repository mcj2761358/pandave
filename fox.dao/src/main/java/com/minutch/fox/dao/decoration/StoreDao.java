package com.minutch.fox.dao.decoration;


import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Store;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface StoreDao extends BaseDao<Store> {

    Store queryByMobilePhone(@Param("mobilePhone")String mobilePhone);
}
