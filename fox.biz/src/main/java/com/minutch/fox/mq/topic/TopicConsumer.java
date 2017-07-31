package com.minutch.fox.mq.topic;

import com.minutch.fox.mq.base.MQConstants;
import com.yuanpin.common.mq.annotaion.MqConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by Minutch on 16/12/13.
 */
@Slf4j
@Service
@MqConsumer
public class TopicConsumer {

    @MqConsumer(tags = MQConstants.TAGS_TOPIC_SEND)
    public void sendTopic(String  topic) {
        System.out.println(topic);
    }
}
