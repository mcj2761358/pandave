package com.minutch.fox.view.decoration;

import com.minutch.fox.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Minutch on 17/9/8.
 */
@Data
public class ReturnOrderView {

    private Long id;
    private Date gmtCreate;
    private String gmtCreatePos;
    private Integer goodsNum;
    private BigDecimal orderAmount;


    private Long headerId;
    private String orderSn;

    private Long cusId;
    private String cusName;
    private String mobilePhone;
    private String houseName;
    private String address;

    private Long goodsId;
    private String goodsName;
    private String goodsModel;
    private BigDecimal goodsPrice;


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
