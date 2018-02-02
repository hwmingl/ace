package com.fighter.ace.cms.entity.external;

import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 18/1/23.
 */
@Data
public class Article extends BaseEntity {

    private String title;
    private Integer type;
    private Integer style;
    private String summary;
    private String content;
    private String source;
    private String author;
    private Integer viewCount;
    private Integer hot;
    private Integer recommend;
    private String picUrl;
    private Integer status;
    private String ext1;
    private String ext2;
    private String ext3;

}

