package com.minutch.fox.entity.system;

 import com.minutch.fox.entity.base.BaseEntity;
 import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class User extends BaseEntity {

    private Long parentId;
    private String userName;
    private String password;
    private String email;
    private Integer sex;
    private Date birthday;
    private Long addressId;
    private String nickName;
    private String qq;
    private String wechat;
    private String mobilePhone;
    private Integer isSeller;
    private Integer isDesigner;
    private Date regTime;
    private String userPhoto;
    private String buyerType;

}
