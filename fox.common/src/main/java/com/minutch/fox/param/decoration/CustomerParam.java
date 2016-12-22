package com.minutch.fox.param.decoration;

import lombok.Data;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class CustomerParam  {
    private Long id;
    private String cusName;
    private String mobilePhone;
    private String houseName;
    private String address;
    private String remark;
}
