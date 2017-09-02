package com.minutch.fox.param.decoration.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Minutch on 17/9/1.
 */
@Data
public class ReturnOrderParam {

    private Long orderId;
    private int goodsNum;
    private BigDecimal orderAmount;
}
