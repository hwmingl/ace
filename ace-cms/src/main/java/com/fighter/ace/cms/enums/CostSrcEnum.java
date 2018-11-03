package com.fighter.ace.cms.enums;

/**
 * Created by hanebert on 16/7/12.
 */
public enum CostSrcEnum {

    REGISTER("MEMBER_REGISTER", "会员注册奖励"),
    ACTIVATION("ACTIVATION_CARD", "积分卡退换"),
    EXTEND("EXTEND", "EXTEND"),
    DOWNLOAD("DOWNLOAD", "下载模型"),
    RECHARGE("RECHARGE", "RECHARGE"),
    CASH("CASH", "提现"),
    CASH_REFUSED("CASH_REFUSED", "提现被拒绝"),
    CASH_FAILED("CASH_FAILED", "提现失败"),
    ADD_COIN_TO_SYS("ADD_COIN_TO_SYS", "系统添加积分");

    private String code;
    private String desc;

    CostSrcEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDesc(String code) {
        for (CostSrcEnum c : CostSrcEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.desc;
            }
        }
        return null;
    }

}
