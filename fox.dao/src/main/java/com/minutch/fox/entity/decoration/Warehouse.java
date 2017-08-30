package com.minutch.fox.entity.decoration;



import com.minutch.fox.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class Warehouse extends BaseEntity {

  private String whName;
  private Long storeId;
  private Long empId;
  private String remark;

}
