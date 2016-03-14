package com.minutch.pandave.biz.topic;

import com.minutch.pandave.entity.topic.Topic;

import java.util.List;

public interface TopicService {
    public List<Topic> getAll();

    public Topic getById(Long id);

    public boolean save(Topic topic);

    public boolean deleteById(Long id);

    public int deleteByIds(Long[] ids);
}
