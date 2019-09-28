package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by Ma Senyu on 2016/7/24 0024.
 * P 类说明
 */
@Data
public class OrderMember extends BaseEntity {
	private Long memberId;
	private String orderNo;
	private String tradeNo;
	private Integer payType;
	private String totalFee;
	private Integer status;
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private String ext5;
	private String nickName;




}
