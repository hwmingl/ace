package com.fighter.ace.code.render.pojo;

/**
 * Created by hanebert on 17/10/31.
 */
public class Property {

    protected boolean primary = false;
    protected String type;
    protected String name;
    protected Integer length;
    protected boolean nullable = true;
    protected String flag = "0";

    public Property() {
    }

    public Property(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimary() {
        return this.primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public Integer getLength() {
        return this.length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

}
