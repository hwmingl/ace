package com.fighter.ace.cms.entity.main;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by kehwa on 2016/8/6.
 */
@Data
public class Area extends BaseEntity {

    private Long id;
    private String name;
    private Long reid;
    private Long disorder;

}

