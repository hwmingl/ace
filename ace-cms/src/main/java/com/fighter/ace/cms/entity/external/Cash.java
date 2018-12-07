package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 16/7/25.
 */
@Data
public class Cash extends BaseEntity {

    private Long memberId;
    private String coins;
    private String charges;
    private String cash;
    private Integer payType;
    private String account;
    private String realName;
    private String phone;
    private String remark;
    private Integer status;


}
