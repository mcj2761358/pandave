package com.minutch.pandave.entity.topic;



import com.minutch.pandave.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class TopicAnswer extends BaseEntity {

  private Long topicId;
  private String platformAid;
  private String authorName;
  private String authorId;
  private String authorSign;
  private String content;
  private String answerCreateTime;
  private String answerModifyTime;
  private Integer voteCount;
  private Integer commentCount;
  private String imgFlag;

}
