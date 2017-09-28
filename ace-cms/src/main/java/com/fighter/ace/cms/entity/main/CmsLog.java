package com.fighter.ace.cms.entity.main;

import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 17/9/25.
 */
@Data
public class CmsLog extends BaseEntity {

    private Long userId;
    private String userName;
    private String ip;
    private String title;
    private String content;
    private Integer status;

}
