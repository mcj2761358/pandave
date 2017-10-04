package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class SubGoods extends BaseEntity {

  private Long goodsId;
  private Long subGoodsId;
  private Integer subNum;
  private String remark;

}
