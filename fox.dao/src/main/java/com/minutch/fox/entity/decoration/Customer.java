package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Customer extends BaseEntity {

  private String cusName;
  private String mobilePhone;
  private String houseName;
  private String address;
  private String remark;

}
