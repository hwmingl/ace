package com.fighter.ace.cms.entity.external;

import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by hanebert on 2019/3/7.
 */
@Data
public class Addr extends BaseEntity {

    private Long memberId;
    private String name;
    private String phone;
    private String addr;
    private Integer status;

}
