package com.minutch.fox.view.decoration;

import com.minutch.fox.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Minutch on 17/8/15.
 */
@Data
public class OrderView {
    private Long id;
    private Date gmtCreate;
    private String gmtCreatePos;
    private Long headerId;
    private Long cusId;
    private String cusName;
    private String mobilePhone;
    private String houseName;
    private String address;
    private String orderSn;
    private Long goodsId;
    private String goodsName;
    private String goodsModel;
    private BigDecimal goodsNum;
    private BigDecimal goodsPrice;
    private BigDecimal inGoodsPrice;
    private BigDecimal orderAmount;
    private BigDecimal returnAmount;
    private Date remindTime;
    private String beFinish;
    private Long storeId;
    private Long empId;
    private String remark;
    private boolean beNew;


    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        if(gmtCreate != null) {
            this.gmtCreatePos = DateUtils.formatDate(gmtCreate, DateUtils.Y_M_D);


            //判断是否当天
            if (DateUtils.formatDate(gmtCreate, DateUtils.Y_M_D).equals(DateUtils.formatDate(new Date(), DateUtils.Y_M_D))) {
                beNew = true;
            }
        }
    }
}
