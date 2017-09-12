package com.minutch.fox.result.decoration;

import com.minutch.fox.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Minutch on 17/8/15.
 */
@Data
public class OrderHeaderVO {

    private Long id;
    private Date gmtCreate;
    private String gmtCreatePos;

    private String cusName;
    private Long cusId;
    private String mobilePhone;
    private String houseName;
    private String address;
    private String orderSn;
    private BigDecimal totalAmount;
    private BigDecimal preAmount;
    private Long storeId;
    private String status;
    private String empName;
    private String remark;
    private boolean beNew;

    private List<OrderVO> orderList;



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
