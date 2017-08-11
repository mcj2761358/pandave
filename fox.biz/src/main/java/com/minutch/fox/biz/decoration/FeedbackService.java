package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.Feedback;

public interface FeedbackService {
  public List<Feedback> getAll();
  
  public Feedback getById(Long id);

  public boolean save(Feedback feedback);

  public boolean deleteById(Long id);

  public  int deleteByIds(Long[] ids);

}
