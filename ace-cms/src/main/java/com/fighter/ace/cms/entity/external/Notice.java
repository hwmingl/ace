package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/5/26.
 * 公告
 */
@Data
public class Notice extends BaseEntity {

    private String title;
    private Integer viewCount;
    private String content;
    private String picUrl;
    private Integer status;


}
