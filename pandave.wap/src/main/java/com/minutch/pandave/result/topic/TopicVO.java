package com.minutch.pandave.result.topic;

import lombok.Data;

/**
 * Created by Minutch on 16/4/22.
 */
@Data
public class TopicVO {
    private Long id;
    private String platformTid;
    private String title;
    private String content;
    private String img;
    private String authorName;
    private String authorId;
    private String authorImg;
    private String topicCreateTime;
    private String topicModifyTime;
    private String lastFollowTime;
    private Integer voteCount;
    private Integer commentCount;
}
