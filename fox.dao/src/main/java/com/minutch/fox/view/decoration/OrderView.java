package com.minutch.fox.view.decoration;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Minutch on 17/8/15.
 */
@Data
public class OrderView {
    private Long id;
    private Long headerId;
    private Long cusId;
    private String cusName;
    private String mobilePhone;
    private String houseName;
    private String address;
    private Long goodsId;
    private String goodsName;
    private String goodsModel;
    private Integer goodsNum;
    private BigDecimal goodsPrice;
    private BigDecimal inGoodsPrice;
    private BigDecimal orderAmount;
    private BigDecimal returnAmount;
    private Date remindTime;
    private String beFinish;
    private Long storeId;
    private Long empId;
    private String remark;
}
