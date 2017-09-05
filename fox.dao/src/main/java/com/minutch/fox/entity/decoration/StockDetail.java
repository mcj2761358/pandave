package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class StockDetail extends BaseEntity {

  private Long goodsId;
  private Integer stockNum;
  private String objType;
  private Long objId;
  private Long storeId;
  private Long empId;
  private String remark;

}
