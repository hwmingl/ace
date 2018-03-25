package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/5/26.
 * 类目
 */
@Data
public class Category extends BaseEntity {

    private String name;
    private Long parentId;
    private Integer deep;
    private Integer status;

}
