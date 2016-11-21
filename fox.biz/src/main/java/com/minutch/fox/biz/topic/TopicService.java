package com.minutch.fox.biz.topic;

import com.minutch.fox.entity.topic.Topic;
import com.minutch.fox.param.topic.TopicQueryParam;
import com.minutch.fox.view.topic.TopicAnswerView;

import java.util.List;

public interface TopicService {
    List<Topic> getAll();

    Topic getById(Long id);

    boolean save(Topic topic);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    int queryTopicCount(TopicQueryParam param);

    List<Topic> queryTopic(TopicQueryParam param);


    int queryTopicAnswerCount(TopicQueryParam param);

    List<TopicAnswerView> queryTopicAnswer(TopicQueryParam param);

    TopicAnswerView queryAnswerDetail(Long answerId);
}
