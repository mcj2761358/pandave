package com.minutch.fox.entity.decoration;

import java.math.BigDecimal;
import java.util.Date;


import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Order extends BaseEntity {

  private Long headerId;
  private Long cusId;
  private String cusName;
  private String mobilePhone;
  private String houseName;
  private String address;
  private Long goodsId;
  private String goodsName;
  private String goodsModel;
  private BigDecimal goodsNum;
  private BigDecimal useNum;
  private Integer handleStock;
  private BigDecimal goodsPrice;
  private BigDecimal inGoodsPrice;
  private BigDecimal orderAmount;
  private BigDecimal returnAmount;
  private Date remindTime;
  private String beFinish;
  private Long storeId;
  private Long empId;
  private String remark;

}
