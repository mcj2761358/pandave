package com.minutch.fox.entity.decoration;

import java.math.BigDecimal;


import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class OrderHeader extends BaseEntity {

  private String cusName;
  private Long cusId;
  private String mobilePhone;
  private String houseName;
  private String address;
  private String orderSn;
  private BigDecimal totalAmount;
  private BigDecimal preAmount;
  private Long storeId;
  private String status;
  private String empName;
  private String remark;

}
