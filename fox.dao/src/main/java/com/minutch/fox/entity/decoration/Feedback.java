package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Feedback extends BaseEntity {

  private String feedType;
  private String feedMessage;
  private String receiveMessage;
  private String remark;

}
