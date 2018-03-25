package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/6/7.
 */
@Data
public class ModelMemCate extends BaseEntity {

    private String name;
    private String keyword;
    private Long categoryId;
    private String categoryName;
    private Long memberId;
    private String memberName;
    private String memberPicUrl;
    private String modelPath;
    private Integer downLimit;
    private String picUrl;

    private Integer coin;
    private Integer point;
    private String style;
    private String size;
    private String length;
    private String width;
    private String height;
    private Integer downCount;
    private Integer rank;
    private String modelNo;
    private String softVersion;
    private Integer status;
    private String mark;



}
