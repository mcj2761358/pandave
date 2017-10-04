package com.minutch.fox.entity.decoration;

import java.math.BigDecimal;


import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Goods extends BaseEntity {

  private String goodsName;
  private String goodsModel;
  private String goodsType;
  private BigDecimal goodsPrice;
  private BigDecimal inGoodsPrice;
  private BigDecimal stockNum;
  private Integer stockWarn;
  private Long whId;
  private String whName;
  private Long storeId;
  private Long empId;
  private String remark;

}
