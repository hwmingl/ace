package com.fighter.ace.cms.entity.external;

import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 18/9/1.
 */
@Data
public class ModelDownload extends BaseEntity{


    private Long memberId;
    private Integer payType;
    private Integer cost;
    private Integer status;
    private String modelName;
    private Long modelId;

}
