package com.minutch.fox.result.decoration;

import com.google.gson.Gson;
import com.minutch.fox.pojo.PermissionRulePO;
import com.minutch.fox.utils.DateUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by Minutch on 17/8/12.
 */
@Data
public class EmployeeVO {
    private Long id;
    private String empMobile;
    private String empPassword;
    private String empName;
    private String address;
    private Date birthday;
    private String birthdayPos;
    private String sex;
    private String cardno;
    private Long storeId;
    private String remark;
    private String permissionRule;
    private PermissionRulePO permissionRulePO;

    public void setPermissionRule(String permissionRule) {
        this.permissionRule = permissionRule;

        if (StringUtils.isNotBlank(permissionRule)) {
            permissionRulePO = new Gson().fromJson(permissionRule, PermissionRulePO.class);
        }

        if (permissionRulePO == null) {
            permissionRulePO = new PermissionRulePO();
        }
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        if (birthday != null) {
            birthdayPos = DateUtils.dateFormat(birthday, DateUtils.Y_M_D);
        }
    }
}
