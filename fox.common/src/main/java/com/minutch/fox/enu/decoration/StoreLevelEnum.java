package com.minutch.fox.enu.decoration;

/**
 * Created by Minutch on 17/8/24.
 */
public enum StoreLevelEnum {

    Free(10,10,10, "体验版"),
    Base(200,200,2000, "家庭版"),
    Star(500,500,5000 , "钻石版"),
    Biu(99999,99999,9999999 ,"至尊版"),
    ;

    private int customerNum;
    private int goodsNum;
    private int orderHeaderNum;
    private String levelName;

    StoreLevelEnum(int customerNum,int goodsNum, int orderHeaderNum, String levelName) {
        this.customerNum = customerNum;
        this.goodsNum = goodsNum;
        this.orderHeaderNum = orderHeaderNum;
        this.levelName = levelName;
    }

    public int getCustomerNum() {
        return customerNum;
    }


    public int getGoodsNum() {
        return goodsNum;
    }

    public int getOrderHeaderNum() {
        return orderHeaderNum;
    }

    public String getLevelName() {
        return levelName;
    }
}
