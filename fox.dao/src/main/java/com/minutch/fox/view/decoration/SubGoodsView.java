package com.minutch.fox.view.decoration;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Minutch on 17/10/3.
 */
@Data
public class SubGoodsView {

    private Long id;
    private Long goodsId;
    private Long subGoodsId;
    private String goodsName;
    private String goodsModel;
    private BigDecimal goodsPrice;
    private int subNum;
}
