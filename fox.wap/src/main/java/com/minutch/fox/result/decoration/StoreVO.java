package com.minutch.fox.result.decoration;

import lombok.Data;

import java.util.Date;

/**
 * Created by Minutch on 17/8/12.
 */
@Data
public class StoreVO {
    private Long id;
    private String storeName;
    private String cardno;
    private String mobilePhone;
    private String password;
    private Date endTime;
    private String remark;
}
