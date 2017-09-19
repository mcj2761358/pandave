package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class StockDetail extends BaseEntity {

  private Long goodsId;
  private BigDecimal stockNum;
  private String objType;
  private Long objId;
  private Long storeId;
  private Long empId;
  private String remark;

}
