package com.minutch.fox.param.decoration;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
    private BigDecimal inGoodsPrice;
    private BigDecimal orderAmount;
    private BigDecimal returnAmount;
    private Date remindTime;
    private String beFinish;
    private String remark;
}
