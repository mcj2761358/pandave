package com.minutch.fox.entity.dance;

import java.util.Date;


import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class StudentSign extends BaseEntity {

  private Long studentId;
  private Date signTime;
  private Long employeeId;

}
