package com.fighter.ace.cms.entity.main;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/5/30.
 */
@Data
public class MarkConfig extends BaseEntity {

    private Boolean on;
    private Integer minWidth;
    private Integer minHeight;
    private String imagePath;
    private String content;
    private Integer size;
    private String color;
    private Integer alpha;
    private Integer pos;
    private Integer offsetX;
    private Integer offsetY;

}
