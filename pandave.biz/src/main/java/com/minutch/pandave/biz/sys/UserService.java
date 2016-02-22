package com.minutch.pandave.biz.sys;


import com.minutch.pandave.entity.system.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User getById(Long id);

    public boolean save(User user);

    public boolean deleteById(Long id);

    public int deleteByIds(Long[] ids);

    public User queryByMobile(String mobilephone);

}
