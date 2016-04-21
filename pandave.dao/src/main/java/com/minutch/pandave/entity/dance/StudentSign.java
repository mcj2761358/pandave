package com.minutch.pandave.entity.dance;

import java.util.Date;


import com.minutch.pandave.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class StudentSign extends BaseEntity {

  private Long studentId;
  private Date signTime;
  private Long employeeId;

}
