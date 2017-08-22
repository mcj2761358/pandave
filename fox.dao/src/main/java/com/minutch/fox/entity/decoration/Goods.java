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
  private BigDecimal goodsPrice;
  private BigDecimal inGoodsPrice;
  private Integer stockNum;
  private Long storeId;
  private Long empId;
  private String remark;

}
