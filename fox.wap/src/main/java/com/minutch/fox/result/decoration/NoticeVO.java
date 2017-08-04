package com.minutch.fox.result.decoration;

import lombok.Data;

import java.util.Date;

/**
 * Created by Minutch on 17/8/4.
 */
@Data
public class NoticeVO {

    private Long id;
    private Date gmtCreate;
    private String niticeType;
    private String noticeMessage;
    private String url;
    private String remark;
}
