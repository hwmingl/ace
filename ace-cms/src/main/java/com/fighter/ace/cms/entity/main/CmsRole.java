package com.fighter.ace.cms.entity.main;

import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 17/9/24.
 */
@Data
public class CmsRole extends BaseEntity {

    /**
     * 角色名称
     */
    private String name;
    /**
     * 是否为超级管理员
     */
    private boolean isSuper;
    /**
     * 对应的权限uri
     */
    private String perms;

}
