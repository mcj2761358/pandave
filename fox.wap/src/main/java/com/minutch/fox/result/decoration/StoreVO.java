package com.minutch.fox.result.decoration;

import com.minutch.fox.enu.decoration.StoreLevelEnum;
import com.minutch.fox.utils.DateUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Minutch on 17/8/12.
 */
@Data
public class StoreVO {
    private Long id;
    private Date gmtCreate;
    private String gmtCreatePos;
    private boolean beNew;

    private String storeName;
    private String cardno;
    private String mobilePhone;
    private String password;
    private String storeLevel;
    private String storeLevelName;
    private Date endTime;
    private String endTimePos;
    private String beAdmin;
    private String remark;
    private List<String> permissionRoleList;

    public void setGmtCreate(Date gmtCreate) {
        if (gmtCreate != null) {
            this.gmtCreate = gmtCreate;
            this.gmtCreatePos = DateUtils.formatDate(gmtCreate, DateUtils.Y_M_D);


            //判断是否当天
            if (DateUtils.formatDate(gmtCreate, DateUtils.Y_M_D).equals(DateUtils.formatDate(new Date(), DateUtils.Y_M_D))) {
                beNew = true;
            }
        }
    }

    public void setEndTime(Date endTime) {
        if (endTime != null) {
            this.endTime = endTime;
            this.endTimePos = DateUtils.formatDate(endTime, DateUtils.Y_M_D);
        }
    }

    public void setStoreLevel(String storeLevel) {
        this.storeLevel = storeLevel;
        if (StringUtils.isNotBlank(storeLevel)) {
            this.storeLevelName = StoreLevelEnum.getLevelName(storeLevel);

        }
    }
}
