package com.minutch.pandave.biz.topic;

import com.minutch.pandave.entity.topic.Topic;
import com.minutch.pandave.param.topic.TopicQueryParam;
import com.minutch.pandave.view.topic.TopicAnswerView;

import java.util.List;

public interface TopicService {
    public List<Topic> getAll();

    public Topic getById(Long id);

    public boolean save(Topic topic);

    public boolean deleteById(Long id);

    public int deleteByIds(Long[] ids);

    public int queryTopicCount(TopicQueryParam param);

    public List<Topic> queryTopic(TopicQueryParam param);


    public int queryTopicAnswerCount(TopicQueryParam param);

    public List<TopicAnswerView> queryTopicAnswer(TopicQueryParam param);

    public TopicAnswerView queryAnswerDetail(Long answerId);
}
