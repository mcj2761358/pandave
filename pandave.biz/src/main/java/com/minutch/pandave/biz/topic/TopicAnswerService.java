package com.minutch.pandave.biz.topic;

import com.minutch.pandave.entity.topic.TopicAnswer;

import java.util.List;

public interface TopicAnswerService {
  public List<TopicAnswer> getAll();
  
  public TopicAnswer getById(Long id);

  public boolean save(TopicAnswer topicAnswer);

  public boolean deleteById(Long id);

  public  int deleteByIds(Long[] ids);

}
