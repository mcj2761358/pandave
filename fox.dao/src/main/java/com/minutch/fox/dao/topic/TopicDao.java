package com.minutch.fox.dao.topic;

import com.minutch.fox.dao.base.BaseDao;
import com.minutch.fox.dao.base.MyBatisRepository;
import com.minutch.fox.entity.topic.Topic;
import com.minutch.fox.param.topic.TopicQueryParam;
import com.minutch.fox.view.topic.TopicAnswerView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface TopicDao extends BaseDao<Topic> {

    public int queryTopicCount(@Param("param")TopicQueryParam param);

    public List<Topic> queryTopic(@Param("param") TopicQueryParam param, @Param("start") int start, @Param("limit") int limit);

    public int queryTopicAnswerCount(@Param("param")TopicQueryParam param);

    public List<TopicAnswerView> queryTopicAnswer(@Param("param") TopicQueryParam param, @Param("start") int start, @Param("limit") int limit);

    public TopicAnswerView queryAnswerDetail(@Param("answerId")Long answerId);
}
