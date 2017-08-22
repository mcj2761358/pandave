package com.minutch.fox.result.decoration;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Minutch on 17/8/19.
 */
@Data
public class StoreGoodsSearchVO {

    private Long id;
    private String goodsName;
    private String goodsModel;
    private BigDecimal goodsPrice;
    private String searchKey;

    public void makeSearchKey() {
        this.searchKey = goodsName+"-"+goodsModel;
    }
}
