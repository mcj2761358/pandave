package com.minutch.fox.param.decoration;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class OrderParam {
    private Long id;

    private Long cusId;
    private String cusName;
    private String mobilePhone;
    private String houseName;
    private String address;
    private String goodsName;
    private String goodsModel;
    private Integer goodsNum;
    private BigDecimal goodsPrice;
    private BigDecimal orderAmount;
    private BigDecimal returnAmount;
    private String beFinish;
    private String remark;
}
