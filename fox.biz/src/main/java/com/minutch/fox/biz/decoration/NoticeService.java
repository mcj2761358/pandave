package com.minutch.fox.biz.decoration;

import com.minutch.fox.entity.decoration.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> getAll();

    Notice getById(Long id);

    boolean save(Notice notice);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    Notice queryNewNotice();
}
