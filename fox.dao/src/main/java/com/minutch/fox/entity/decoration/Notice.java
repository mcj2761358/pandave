package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Notice extends BaseEntity {

  private String niticeType;
  private String noticeMessage;
  private String url;
  private String remark;

}
