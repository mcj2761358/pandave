package com.minutch.fox.biz.dance;

import com.minutch.fox.entity.dance.StudentSign;

import java.util.List;

public interface StudentSignService {
  List<StudentSign> getAll();
  
  StudentSign getById(Long id);

  boolean save(StudentSign studentSign);

  boolean deleteById( Long id);

   int deleteByIds(Long[] ids);

}
