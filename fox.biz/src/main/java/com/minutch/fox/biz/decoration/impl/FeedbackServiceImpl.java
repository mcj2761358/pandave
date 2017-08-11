package com.minutch.fox.biz.decoration.impl;

import java.util.List;

import com.minutch.fox.biz.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minutch.fox.biz.decoration.FeedbackService;
import com.minutch.fox.dao.decoration.FeedbackDao;
import com.minutch.fox.entity.decoration.Feedback;


@Service
public class FeedbackServiceImpl extends BaseServiceImpl implements FeedbackService{

  @Autowired
  private FeedbackDao feedbackDao;

  public List<Feedback> getAll() {
    return super.getAll(feedbackDao);
  }

  public Feedback getById(Long id) {
    return super.getById(feedbackDao, id);
  }

  public boolean save(Feedback feedback) {
    return super.save(feedbackDao, feedback);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(feedbackDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(feedbackDao, ids);
  }
}
