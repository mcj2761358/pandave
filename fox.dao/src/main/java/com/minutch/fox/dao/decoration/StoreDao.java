package com.minutch.fox.dao.decoration;


import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Store;
import com.minutch.fox.param.decoration.StoreQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface StoreDao extends BaseDao<Store> {

    Store queryByMobilePhone(@Param("mobilePhone")String mobilePhone);

    int updatePassword(@Param("id")Long id,@Param("password")String password);

    List<Store> queryStore(@Param("param") StoreQueryParam param, @Param("start") int start, @Param("limit") int limit);

    int queryStoreCount(@Param("param") StoreQueryParam param);
}
