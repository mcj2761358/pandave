package com.minutch.fox.entity.dance;

import java.util.Date;
import java.math.BigDecimal;


import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Student extends BaseEntity {

  private String name;
  private String photo;
  private Date birthday;
  private String cardno;
  private String mobilePhone;
  private String courseTerm;
  private String courseType;
  private String courseName;
  private Integer totalCourse;
  private Integer usedCourse;
  private BigDecimal courseFee;
  private BigDecimal realCourseFee;
  private Date lastSignTime;

}
