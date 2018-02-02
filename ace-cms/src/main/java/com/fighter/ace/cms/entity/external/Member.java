package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * Created by hanebert on 16/5/26.
 * 会员
 */
@Data
public class Member extends BaseEntity {

    private String userName;
    private String userPwd;
    private String realName;
    private String nickName;
    private String email;
    private String picUrl;
    private String phone;
    private String cardNo;
    private Integer sex;
    private Date birthday;
    private String province;
    private String city;
    private String coin;
    private String point;
    private String modelPath;
    private String contractPath;
    private Integer viewCount;
    private Integer type;
    private Integer status;
    private String qq;
    private String ext1;
    private String ext2;
    private String ext3;



}
