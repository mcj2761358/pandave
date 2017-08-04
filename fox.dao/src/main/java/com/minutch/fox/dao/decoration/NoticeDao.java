package com.minutch.fox.dao.decoration;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.decoration.Notice;

@MyBatisRepository
public interface NoticeDao extends BaseDao<Notice> {

    Notice queryNewNotice();
}
