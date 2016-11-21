package com.minutch.fox.result.dance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Minutch on 16/3/19.
 */
@Data
public class StudentVO {

    private Long id;
    private String name;
    private String photo;
    private Date birthday;
    private String cardno;
    private String mobilePhone;
    private String courseTerm;
    private String courseType;
    private String courseName;
    private Integer totalCourse;
    private Integer usedCourse;
    private BigDecimal courseFee;
    private BigDecimal realCourseFee;
    private Date lastSignTime;
}
