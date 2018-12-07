package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/7/23.
 */
@Data
public class Orders extends BaseEntity {

    private Long memberId;
    private String orderNo;
    private String tradeNo;
    private Integer payType;
    private String totalFee;
    private Integer status;
    private String ext1;
    private String ext2;
    private String ext3;


}
