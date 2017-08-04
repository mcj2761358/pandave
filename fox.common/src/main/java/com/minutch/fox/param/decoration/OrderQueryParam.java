package com.minutch.fox.param.decoration;

import com.minutch.fox.param.PageParam;
import lombok.Data;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class OrderQueryParam extends PageParam {
    private String keyword;
    private Long cusId;
    private Long storeId;

    private Integer remindStatus;
    private String timeName; //近三日
    private String queryTimeStart; //查询开始日期
    private String queryTimeEnd; //查询截止日期
}
