package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by Ma Senyu on 2016/7/24 0024.
 * P 类说明
 */
@Data
public class Coins extends BaseEntity {
	private Long memberId;
	private String source;
	private String current;
	private String mark;
	private String finalized;
	private Integer status;
	private String remark;


}
