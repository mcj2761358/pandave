package com.minutch.fox.biz.dance.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.dance.StudentSignService;
import com.minutch.fox.dao.dance.StudentSignDao;
import com.minutch.fox.entity.dance.StudentSign;


@Service
public class StudentSignServiceImpl extends BaseServiceImpl implements StudentSignService{

  @Autowired
  private StudentSignDao studentSignDao;

  public List<StudentSign> getAll() {
    return super.getAll(studentSignDao);
  }

  public StudentSign getById(Long id) {
    return super.getById(studentSignDao, id);
  }

  public boolean save(StudentSign studentSign) {
    return super.save(studentSignDao, studentSign);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(studentSignDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(studentSignDao, ids);
  }
}
