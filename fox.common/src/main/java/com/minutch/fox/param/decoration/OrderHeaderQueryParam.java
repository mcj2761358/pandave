package com.minutch.fox.param.decoration;

import com.minutch.fox.param.PageParam;
import lombok.Data;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class OrderHeaderQueryParam extends PageParam {

    private String keyword;
    private Long storeId;

    private String beFinish;
    private String queryTime; //按日期查询
    private String gmtCreateStart; //按日期查询
    private String gmtCreateEnd; //按日期查询
}
