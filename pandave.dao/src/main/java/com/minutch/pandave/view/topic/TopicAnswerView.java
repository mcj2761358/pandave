package com.minutch.pandave.view.topic;

import lombok.Data;

/**
 * Created by Minutch on 16/4/22.
 */
@Data
public class TopicAnswerView {

    private Long topicId;
    private String topicTitle;
    private String topicContent;
    private Long answerId;
    private String answerModifyTime;
    private String answerAuthorName;
    private String answerAuthorSign;
    private String answerContent;
    private String answerContentBrief;
    private int answerVote;
}
