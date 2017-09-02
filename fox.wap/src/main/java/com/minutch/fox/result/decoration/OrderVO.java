package com.minutch.fox.result.decoration;

import com.minutch.fox.utils.DateUtils;
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
    private Long headerId;
    private String cusName;


    private Date gmtCreate;
    private String gmtCreatePos;
    private String mobilePhone;
    private String houseName;
    private String address;
    private String orderSn;
    private Long goodsId;
    private String goodsName;
    private String goodsModel;
    private Integer goodsNum;
    private BigDecimal goodsPrice;
    private BigDecimal inGoodsPrice;
    private BigDecimal orderAmount;
    private Date remindTime;
    private String remindTimePos;
    private BigDecimal returnAmount;
    private String beFinish;
    private Long storeId;
    private Long empId;
    private String remark;
    private boolean beNew;


    private BigDecimal totalAmount;
    private BigDecimal preAmount;

    public void setGoodsModel(String  goodsModel) {
        if (goodsModel == null) {
            this.goodsModel = "";
        } else  {
            this.goodsModel = goodsModel;
        }
    }

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

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
        if(remindTime != null) {
            this.remindTimePos = DateUtils.formatDate(remindTime, DateUtils.Y_M_D);
        }
    }
}
