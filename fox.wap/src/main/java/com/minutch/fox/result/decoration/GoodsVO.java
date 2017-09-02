package com.minutch.fox.result.decoration;

import com.minutch.fox.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Minutch on 17/8/19.
 */
@Data
public class GoodsVO {

    private Long id;
    private Date gmtCreate;
    private String gmtCreatePos;
    private String goodsName;
    private String goodsModel;
    private BigDecimal goodsPrice;
    private BigDecimal inGoodsPrice;
    private Integer stockNum;
    private Long whId;
    private String whName;
    private Long storeId;
    private Long empId;
    private String remark;
    private boolean beNew;

    public void setGmtCreate(Date gmtCreate) {
        if (gmtCreate != null) {
            this.gmtCreate = gmtCreate;
            this.gmtCreatePos = DateUtils.formatDate(gmtCreate, DateUtils.Y_M_D);


            //判断是否当天
            if (DateUtils.formatDate(gmtCreate, DateUtils.Y_M_D).equals(DateUtils.formatDate(new Date(), DateUtils.Y_M_D))) {
                beNew = true;
            }
        }
    }
}
