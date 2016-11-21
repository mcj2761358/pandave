package com.minutch.fox.biz.topic;

import com.minutch.fox.entity.topic.TopicAnswer;

import java.util.List;

public interface TopicAnswerService {
  List<TopicAnswer> getAll();
  
  TopicAnswer getById(Long id);

  boolean save(TopicAnswer topicAnswer);

  boolean deleteById(Long id);

   int deleteByIds(Long[] ids);

}
