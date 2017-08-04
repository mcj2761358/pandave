package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.decoration.NoticeService;
import com.minutch.fox.dao.decoration.NoticeDao;
import com.minutch.fox.entity.decoration.Notice;


@Service
public class NoticeServiceImpl extends BaseServiceImpl implements NoticeService{

  @Autowired
  private NoticeDao noticeDao;

  public List<Notice> getAll() {
    return super.getAll(noticeDao);
  }

  public Notice getById(Long id) {
    return super.getById(noticeDao, id);
  }

  public boolean save(Notice notice) {
    return super.save(noticeDao, notice);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(noticeDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(noticeDao, ids);
  }

  @Override
  public Notice queryNewNotice() {
    return noticeDao.queryNewNotice();
  }
}
