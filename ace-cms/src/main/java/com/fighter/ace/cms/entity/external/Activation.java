package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * Created by hanebert on 16/6/27.
 * 激活码
 */
@Data
public class Activation extends BaseEntity {

    private String code;
    private Integer point;
    private Date expiredTime;
    private String sourceIp;
    private Long memberId;
    private String memberName;
    private Integer status;


}
