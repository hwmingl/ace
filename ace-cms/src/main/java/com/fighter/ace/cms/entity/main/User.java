package com.fighter.ace.cms.entity.main;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * Created by hanebert on 16/5/4.
 */
@Data
public class User extends BaseEntity {

    private String userName;
    private String userPwd;
    private String realName;
    private String role;
    private Date lastLoginTime;
    private Integer status;

}
