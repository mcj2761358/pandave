package com.minutch.fox.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minutch on 16/3/19.
 */
@Slf4j
public class FoxBeanUtils {
    public static <T> List<T> copyList(List<?> entityList, Class<T> voClass) {

        if (entityList == null) {
            return null;
        }
        if (entityList.size() == 0) {
            return new ArrayList<>();
        }
        List<T> voList = new ArrayList<T>();
        T voObj = null;

        for (Object entity : entityList) {
            try {
                voObj = voClass.newInstance();
            } catch (Exception e) {
                log.error("copyList:new instance error!", e);
            }
            try {
                BeanUtils.copyProperties(entity, voObj);
            } catch (Exception e) {
                log.error("copyList:复制出错!", e);
            }
            voList.add(voObj);
        }

        return voList;
    }
}
