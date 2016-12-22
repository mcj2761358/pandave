package com.minutch.fox.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by Minutch on 15/6/25.
 */
public class DataCheckUtils {
    public static boolean checkMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
        boolean flag = p.matcher(mobile.trim()).find();
        return flag;
    }

    /**
     * 检查电话号
     * @param telephone
     * @return
     */
    public static boolean checkTelephone(String telephone) {
        if (StringUtils.isBlank(telephone)) {
            return false;
        }

        if (telephone.length()<7) {
            return false;
        }
        Pattern p = Pattern.compile("^\\d+$");
        boolean flag = p.matcher(telephone).find();
        return flag;
    }
}
