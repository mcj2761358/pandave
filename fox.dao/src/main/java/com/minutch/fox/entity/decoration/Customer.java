package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = false)
@Data
public class Customer extends BaseEntity {

  private String cusName;
  private String mobilePhone;
  private String houseName;
  private String address;
  private Long storeId;
  private Long empId;
  private BigDecimal totalAmount;
  private BigDecimal preAmount;
  private String remark;

}
