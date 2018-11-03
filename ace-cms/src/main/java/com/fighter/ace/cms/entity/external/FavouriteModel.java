package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/6/27.
 */
@Data
public class FavouriteModel extends BaseEntity {

    private Long memberId;
    private Long modelId;
    private String modelName;
    private Integer status;

}
