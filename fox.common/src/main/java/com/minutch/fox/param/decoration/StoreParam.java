package com.minutch.fox.param.decoration;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class StoreParam {
    private Long id;

    private String storeName;
    private String cardno;
    private String mobilePhone;
    private String password;
    private String storeLevel;
    private Date endTime;
    private String remark;

    private List<String> permissionRoleList;
}
