package com.minutch.fox.enu.decoration;

import java.util.LinkedHashMap;

/**
 * Created by Minutch on 17/8/24.
 */
public enum StoreLevelEnum {

    Free(10,10,10, 2,"体验版"),
    Base(200,200,2000, 3, "家庭版"),
    Star(500,500,5000 , 6,"钻石版"),
    Biu(99999,99999,9999999 , 999,"至尊版"),
    ;

    private static LinkedHashMap<String,String> nameMap = new LinkedHashMap<>();

    static {
        nameMap.put("Free","体验版");
        nameMap.put("Base","家庭版");
        nameMap.put("Star","钻石版");
        nameMap.put("Biu","至尊版");
    }

    private int customerNum;
    private int goodsNum;
    private int orderHeaderNum;
    private int employeeNum;
    private String levelName;


    StoreLevelEnum(int customerNum, int goodsNum, int orderHeaderNum, int employeeNum, String levelName) {
        this.customerNum = customerNum;
        this.goodsNum = goodsNum;
        this.orderHeaderNum = orderHeaderNum;
        this.employeeNum = employeeNum;
        this.levelName = levelName;
    }

    public static String getLevelName(String typeName){

        return nameMap.get(typeName);
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

    public int getEmployeeNum() {
        return employeeNum;
    }

    public String getLevelName() {
        return levelName;
    }
}
