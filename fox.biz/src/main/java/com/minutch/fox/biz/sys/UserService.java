package com.minutch.fox.biz.sys;


import com.minutch.fox.entity.system.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(Long id);

    boolean save(User user);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    User queryByMobile(String mobilephone);

}
