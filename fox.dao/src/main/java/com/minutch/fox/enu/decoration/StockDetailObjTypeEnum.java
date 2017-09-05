package com.minutch.fox.enu.decoration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minutch on 17/9/3.
 */
public enum StockDetailObjTypeEnum {

    goodsIn("商品入库"),
    order("订单出单"),
    orderDelete("订单删除"),
    orderReturn("订单退货"),;

    private static Map<String,String> nameMap = new HashMap<>();

    static {
        nameMap.put("goodsIn","商品入库");
        nameMap.put("order","订单出单");
        nameMap.put("orderDelete","订单删除");
        nameMap.put("orderReturn","订单退货");
    }

    private String typeName;


    StockDetailObjTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public static String getTypeName(String typeName){

        return nameMap.get(typeName);
    }

    public String getTypeName() {
        return typeName;
    }
}
