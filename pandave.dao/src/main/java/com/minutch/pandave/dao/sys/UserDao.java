package com.minutch.pandave.dao.sys;

import com.minutch.pandave.dao.base.BaseDao;
import com.minutch.pandave.dao.base.MyBatisRepository;
import com.minutch.pandave.entity.system.User;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface UserDao extends BaseDao<User> {

    User queryByMobile(@Param("mobilephone") String mobilephone);

}
