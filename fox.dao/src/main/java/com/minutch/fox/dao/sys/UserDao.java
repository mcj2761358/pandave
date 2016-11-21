package com.minutch.fox.dao.sys;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.system.User;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface UserDao extends BaseDao<User> {

    User queryByMobile(@Param("mobilephone") String mobilephone);

}
