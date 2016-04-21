package com.minutch.pandave.entity.dance;



import com.minutch.pandave.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Employee extends BaseEntity {

  private String name;
  private String cardno;
  private String mobilePhone;
  private String password;

}
