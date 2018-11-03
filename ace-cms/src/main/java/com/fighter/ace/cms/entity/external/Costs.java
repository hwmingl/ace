package com.fighter.ace.cms.entity.external;


import com.fighter.ace.framework.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by Ma Senyu on 2016/7/24 0024.
 * P 类说明
 */
@Data
public class Costs extends BaseEntity {
    /**
     * 会员
     */
	private Long memberId;
    /**
     * 1金币2积分
     */
    private Integer type;
    /**
     * 金币或积分来源
     */
	private String source;
    /**
     * 现有金币或积分
     */
	private String current;
    /**
     * 新增或扣除
     */
	private String mark;
    /**
     * 最终金币
     */
	private String finalized;
	private Integer status;
	private String remark;


}
