package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/8/6.
 */
@Data
public class Banner extends BaseEntity {

    private String title;
    private String picUrl;
    private String link;
    private Integer status;

}
