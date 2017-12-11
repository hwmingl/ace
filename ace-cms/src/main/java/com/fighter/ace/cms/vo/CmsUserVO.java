package com.fighter.ace.cms.vo;

import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * Created by hanebert on 17/10/22.
 */
@Data
public class CmsUserVO extends BaseEntity {

    private String userName;
    private String userPwd;
    private String realName;
    private String roleName;
    private Date lastLoginTime;
    private Integer status;


}
