package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/5/26.
 * 模型
 */
@Data
public class Model extends BaseEntity {

    private String name;
    private String keyword;
    private Long categoryId;
    private Long memberId;
    private String modelPath;
    private Integer downLimit;
    private String picUrl;
    private String picUrls;

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
    private String mark;
    private Integer status;
    private String ext1;
    private String ext2;
    private String ext3;

}
