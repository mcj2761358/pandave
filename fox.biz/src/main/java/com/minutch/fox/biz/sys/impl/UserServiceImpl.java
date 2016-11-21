package com.minutch.fox.biz.sys.impl;


import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.sys.UserService;
import com.minutch.fox.dao.sys.UserDao;
import com.minutch.fox.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAll() {
        return super.getAll(userDao);
    }

    public User getById(Long id) {
        return super.getById(userDao, id);
    }

    public boolean save(User user) {
        return super.save(userDao, user);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(userDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(userDao, ids);
    }

    public User queryByMobile(String mobilephone) {
        return userDao.queryByMobile(mobilephone);
    }
}
