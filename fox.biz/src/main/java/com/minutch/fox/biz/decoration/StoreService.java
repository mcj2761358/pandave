package com.minutch.fox.biz.decoration;


import com.minutch.fox.entity.decoration.Store;
import com.minutch.fox.enu.decoration.StoreLevelEnum;
import com.minutch.fox.param.decoration.StoreQueryParam;

import java.util.List;

public interface StoreService {
    List<Store> getAll();

    Store getById(Long id);

    boolean save(Store store);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    Store queryByMobilePhone(String mobilePhone);

    int updatePassword(Long id, String password);

    StoreLevelEnum queryStoreLevel(Long storeId);

    List<Store> queryStore(StoreQueryParam param);

    int queryStoreCount(StoreQueryParam param);

    boolean isAdmin(Long storeId);
}
