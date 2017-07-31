package com.minutch.fox.mq.topic;

import com.minutch.fox.mq.base.MQConstants;
//import com.yuanpin.common.mq.annotaion.MqProducer;
//import com.yuanpin.common.mq.producer.SqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by Minutch on 16/12/13.
 */
@Slf4j
@Service
//@MqProducer
public class TopicProducer {

//    @MqProducer(tags = MQConstants.TAGS_TOPIC_SEND)
//    public SqMessage sendTopic(String a) {
//        System.out.println("produce--" + a);
//        SqMessage sqMessage = new SqMessage(a, a);
//        return sqMessage;
//    }
}
