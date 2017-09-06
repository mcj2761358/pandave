package com.minutch.fox.view.decoration;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Minutch on 17/9/6.
 */
@Data
public class OrderHeaderAmountView {
    private String gmtCreate;
    private BigDecimal totalAmount;
}
