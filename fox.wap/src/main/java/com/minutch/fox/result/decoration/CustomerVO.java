package com.minutch.fox.result.decoration;

import lombok.Data;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class CustomerVO {
    private Long id;
    private String cusName;
    private String mobilePhone;
    private String houseName;
    private String address;
    private String remark;
}
