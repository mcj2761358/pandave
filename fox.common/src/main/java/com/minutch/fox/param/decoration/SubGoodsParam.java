package com.minutch.fox.param.decoration;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class SubGoodsParam {

    private Long goodsId;
    private Long subGoodsId;
    private String goodsName;
    private String goodsModel;
    private Integer subNum;
}
