package com.minutch.pandave.biz.dance;

import com.minutch.pandave.entity.dance.StudentSign;

import java.util.List;

public interface StudentSignService {
  public List<StudentSign> getAll();
  
  public StudentSign getById(Long id);

  public boolean save(StudentSign studentSign);

  public boolean deleteById( Long id);

  public  int deleteByIds(Long[] ids);

}
