package com.minutch.fox.param.decoration;

import com.minutch.fox.param.PageParam;
import lombok.Data;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class GoodsQueryParam extends PageParam {
    private String keyword;
    private Long storeId;
    private String goodsType;
}
