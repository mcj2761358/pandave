package com.minutch.fox.entity.decoration;

import java.math.BigDecimal;


import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class ReturnOrder extends BaseEntity {

  private Long orderId;
  private Integer goodsNum;
  private BigDecimal orderAmount;
  private Long storeId;
  private Long empId;
  private String remark;

}
