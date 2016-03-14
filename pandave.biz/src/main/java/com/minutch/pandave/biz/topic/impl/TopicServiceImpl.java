package com.minutch.pandave.biz.topic.impl;

import com.minutch.pandave.biz.base.BaseServiceImpl;
import com.minutch.pandave.biz.topic.TopicService;
import com.minutch.pandave.dao.topic.TopicDao;
import com.minutch.pandave.entity.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TopicServiceImpl extends BaseServiceImpl implements TopicService {

  @Autowired
  private TopicDao topicDao;

  public List<Topic> getAll() {
    return super.getAll(topicDao);
  }

  public Topic getById(Long id) {
    return super.getById(topicDao, id);
  }

  public boolean save(Topic topic) {
    return super.save(topicDao, topic);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(topicDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(topicDao, ids);
  }
}
