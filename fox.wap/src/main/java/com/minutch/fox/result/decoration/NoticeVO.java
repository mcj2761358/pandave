package com.minutch.fox.result.decoration;

import com.minutch.fox.enu.decoration.NoticeTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by Minutch on 17/8/4.
 */
@Data
public class NoticeVO {

    private Long id;
    private Date gmtCreate;
    private String noticeType;
    private String noticeTypeName;
    private String noticeMessage;
    private String url;
    private String remark;

    public void setNoticeType(String noticeType) {
        if (StringUtils.isNotBlank(noticeType)) {
            this.noticeTypeName = NoticeTypeEnum.getTypeName(noticeType);
        }
        this.noticeType = noticeType;
    }
}
