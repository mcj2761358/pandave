package com.minutch.fox.entity.decoration;

import java.util.Date;


import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Store extends BaseEntity {

  private String storeName;
  private String cardno;
  private String mobilePhone;
  private String password;
  private String storeLevel;
  private Date endTime;
  private String beAdmin;
  private String remark;

}
