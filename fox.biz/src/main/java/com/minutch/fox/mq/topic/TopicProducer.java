package com.minutch.fox.mq.topic;

import com.minutch.fox.entity.topic.Topic;
import com.minutch.fox.mq.base.MQConstants;
import com.yuanpin.common.mq.annotaion.MqProducer;
import com.yuanpin.common.mq.producer.SqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by Minutch on 16/12/13.
 */
@Slf4j
@Service
@MqProducer
public class TopicProducer {

    @MqProducer(tags = MQConstants.TAGS_TOPIC_SEND)
    public SqMessage sendTopic(Topic topic) {
        System.out.println("produce--" + topic);
        SqMessage sqMessage = new SqMessage(topic.getId().toString(), topic);
        return sqMessage;
    }
}
