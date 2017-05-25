package com.fighter.ace.framework.common.po;

import java.util.Date;

/**
 * Created by hanebert on 17/5/25.
 */
public class BaseDO implements java.io.Serializable {

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
