package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class BaseGoodsName extends BaseEntity {

  private String goodsName;
  private Long storeId;
  private String remark;

}
