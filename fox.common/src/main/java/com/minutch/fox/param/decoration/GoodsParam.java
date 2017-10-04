package com.minutch.fox.param.decoration;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class GoodsParam {

    private Long id;
    private String goodsName;
    private String goodsModel;
    private String goodsType;
    private BigDecimal goodsPrice;
    private BigDecimal inGoodsPrice;
    private BigDecimal stockNum;
    private Integer stockWarn;
    private Long whId;
    private String whName;

    private String remark;
}
