package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/5/26.
 * 我的收藏
 */
@Data
public class Favourite extends BaseEntity {

    private Long memberId;
    private Long modelId;
    private Integer status;


}
