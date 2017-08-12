package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Employee extends BaseEntity {

  private String empMobile;
  private String empPassword;
  private String empName;
  private Long storeId;
  private String remark;
  private String permissionRule;

}
