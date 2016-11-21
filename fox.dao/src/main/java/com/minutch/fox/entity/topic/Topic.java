package com.minutch.fox.entity.topic;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Topic extends BaseEntity {

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
