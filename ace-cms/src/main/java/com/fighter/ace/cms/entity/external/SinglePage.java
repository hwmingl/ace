package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/5/30.
 */
@Data
public class SinglePage extends BaseEntity {

    private String title;
    private String content;
    private int status;

}
