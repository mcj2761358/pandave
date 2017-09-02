package com.minutch.fox.param.decoration;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Minutch on 16/12/17.
 */
@Data
public class EmployeeParam {

    private Long id;
    private String empMobile;
    private String empPassword;
    private String empName;
    private String address;
    private Date birthday;
    private String sex;
    private String cardno;
    private Long storeId;
    private String remark;
}
