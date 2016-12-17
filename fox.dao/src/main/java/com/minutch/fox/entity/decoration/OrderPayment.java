package com.minutch.fox.entity.decoration;

import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class OrderPayment extends BaseEntity {

  private Long orderId;
  private BigDecimal returnAmount;
  private String remark;

}
