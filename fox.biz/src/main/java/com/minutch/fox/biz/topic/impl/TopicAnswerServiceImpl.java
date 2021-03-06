package com.minutch.fox.biz.topic.impl;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.topic.TopicAnswerService;
import com.minutch.fox.dao.topic.TopicAnswerDao;
import com.minutch.fox.entity.topic.TopicAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TopicAnswerServiceImpl extends BaseServiceImpl implements TopicAnswerService {

  @Autowired
  private TopicAnswerDao topicAnswerDao;

  public List<TopicAnswer> getAll() {
    return super.getAll(topicAnswerDao);
  }

  public TopicAnswer getById(Long id) {
    return super.getById(topicAnswerDao, id);
  }

  public boolean save(TopicAnswer topicAnswer) {
    return super.save(topicAnswerDao, topicAnswer);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(topicAnswerDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(topicAnswerDao, ids);
  }
}
