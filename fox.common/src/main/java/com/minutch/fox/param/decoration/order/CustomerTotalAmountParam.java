package com.minutch.fox.param.decoration.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Minutch on 17/8/8.
 */
@Data
public class CustomerTotalAmountParam {

    private BigDecimal totalAmount;
    private BigDecimal preAmount;
    private Long headerId;
    private Long storeId;
}
