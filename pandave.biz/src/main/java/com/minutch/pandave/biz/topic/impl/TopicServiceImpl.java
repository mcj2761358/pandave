package com.minutch.pandave.biz.topic.impl;

import com.minutch.pandave.biz.base.BaseServiceImpl;
import com.minutch.pandave.biz.topic.TopicService;
import com.minutch.pandave.dao.topic.TopicDao;
import com.minutch.pandave.entity.topic.Topic;
import com.minutch.pandave.param.topic.TopicQueryParam;
import com.minutch.pandave.view.topic.TopicAnswerView;
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

    @Override
    public int queryTopicCount(TopicQueryParam param) {
        return topicDao.queryTopicCount(param);
    }

    @Override
    public List<Topic> queryTopic(TopicQueryParam param) {
        return topicDao.queryTopic(param, param.getStart(), param.getEnd());
    }

    @Override
    public int queryTopicAnswerCount(TopicQueryParam param) {
        return topicDao.queryTopicAnswerCount(param);
    }

    @Override
    public List<TopicAnswerView> queryTopicAnswer(TopicQueryParam param) {
        return topicDao.queryTopicAnswer(param, param.getStart(), param.getEnd());
    }

    @Override
    public TopicAnswerView queryAnswerDetail(Long answerId) {
        return topicDao.queryAnswerDetail(answerId);
    }
}
