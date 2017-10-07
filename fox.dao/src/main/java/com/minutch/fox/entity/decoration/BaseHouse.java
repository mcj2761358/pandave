package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class BaseHouse extends BaseEntity {

  private String houseName;
  private Long storeId;
  private String remark;

}
