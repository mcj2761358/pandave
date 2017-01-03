package com.minutch.fox.entity.decoration;

import java.math.BigDecimal;


import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Order extends BaseEntity {

  private Long cusId;
  private String cusName;
  private String mobilePhone;
  private String houseName;
  private String address;
  private String goodsName;
  private Integer goodsNum;
  private BigDecimal goodsPrice;
  private BigDecimal orderAmount;
  private BigDecimal returnAmount;
  private String beFinish;
  private String remark;

}
