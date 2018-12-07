package com.fighter.ace.cms.enums;

/**
 * Created by hanebert on 16/7/23.
 */
public enum PayType {

    AliPay("AliPay", 1);
    PayType(String name, Integer value) {
        this.value = value;
        this.name = name;
    }

    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static Integer getValue(String name) {
        for (PayType c : PayType.values()) {
            if (c.getName().equals(name)) {
                return c.value;
            }
        }
        return null;
    }

}
