package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by kehwa on 2016/6/5.
 */
@Data
public class Mall extends BaseEntity {

    private String name;
    private Integer type;
    private String price;
    private String picUrl;

    private String size;
    private String material;

    private String model;
    private String label;
    private String mark;
    private String series;
    private Integer status;


}

