package com.minutch.fox.enu.decoration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minutch on 17/8/4.
 */
public enum NoticeTypeEnum {

    system("系统通知"),
    activity("活动通知"),
    relax("轻松一刻"),
    bible("每日经文"),
    ;

    private static Map<String,String> nameMap = new HashMap<>();

    static {
        nameMap.put("system","系统通知");
        nameMap.put("activity","活动通知");
        nameMap.put("relax","轻松一刻");
        nameMap.put("bible","每日经文");
    }

    private String typeName;


    NoticeTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public static String getTypeName(String typeName){

        return nameMap.get(typeName);
    }

    public String getTypeName() {
        return typeName;
    }
}
