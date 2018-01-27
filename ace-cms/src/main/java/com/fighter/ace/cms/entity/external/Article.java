package com.fighter.ace.cms.entity.external;

import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 18/1/23.
 */
@Data
public class Article extends BaseEntity {

    private String title;
    private int type;
    private String summary;
    private String content;
    private String source;
    private String author;
    private int viewCount;
    private int hot;
    private int recommend;
    private String picUrl;
    private int status;
    private String ext1;
    private String ext2;
    private String ext3;

}

