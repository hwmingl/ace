package com.fighter.ace.cms.entity.main;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/7/24.
 */
@Data
public class Dict extends BaseEntity {

    private String name;
    private String data;
    private String remark;
    private String operator;
    private Integer status;


}
