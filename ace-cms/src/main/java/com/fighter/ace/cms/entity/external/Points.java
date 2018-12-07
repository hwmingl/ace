package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/7/12.
 * 会员积分记录
 */
@Data
public class Points extends BaseEntity {

    private Long memberId;
    private String source;
    private String current;
    private String mark;
    private String finalized;
    private Integer status;
    private String remark;

}
