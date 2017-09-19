package com.minutch.fox.result.decoration;

import com.minutch.fox.enu.decoration.StockDetailObjTypeEnum;
import com.minutch.fox.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Minutch on 17/9/3.
 */
@Data
public class StockDetailVO {

    private Long id;
    private Date gmtCreate;
    private String gmtCreatePos;
    private Long goodsId;
    private BigDecimal stockNum;
    private String objType;
    private String objName;
    private Long objId;

    public void setObjType(String objType) {
        if (objType!=null) {
            this.objType = objType;
            this.objName = StockDetailObjTypeEnum.getTypeName(objType);
        }
    }

    public void setGmtCreate(Date gmtCreate) {
        if (gmtCreate != null) {
            this.gmtCreate = gmtCreate;
            this.gmtCreatePos = DateUtils.formatDate(gmtCreate, DateUtils.Y_M_D);
        }
    }
}
