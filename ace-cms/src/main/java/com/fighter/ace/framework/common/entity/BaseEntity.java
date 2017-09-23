package com.fighter.ace.framework.common.entity;

import java.util.Date;

/**
 * Created by hanebert on 16/4/11.
 * 基础实体类,包含各实体公共属性
 */
public class BaseEntity implements java.io.Serializable{

    /** 主键 **/
    private Long id;
    /** 创建时间 **/
    private Date createTime;
    /** 修改时间 **/
    private Date modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
