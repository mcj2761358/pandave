package com.minutch.fox.result.decoration;

import com.minutch.fox.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class CustomerVO {
    private Long id;
    private String cusName;
    private Date gmtCreate;
    private String gmtCreatePos;
    private String mobilePhone;
    private String houseName;
    private String address;
    private String remark;

    public void setGmtCreate(Date gmtCreate) {
        if (gmtCreate != null) {
            this.gmtCreate = gmtCreate;
            this.gmtCreatePos = DateUtils.formatDate(gmtCreate, DateUtils.Y_M_D);
        }
    }
}
