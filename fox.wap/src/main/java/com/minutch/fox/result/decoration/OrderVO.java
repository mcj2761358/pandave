package com.minutch.fox.result.decoration;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class OrderVO {
    private Long id;
    private Long cusId;
    private String cusName;
    private Date gmtCreate;
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
