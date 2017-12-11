package com.fighter.ace.code.render.pojo.hibernate;

import com.fighter.ace.code.render.pojo.POJO;

/**
 * Created by hanebert on 17/10/31.
 */
public class HibernateConfig extends POJO {

    protected String tableName;

    public HibernateConfig(String clazzName, String tableName) {
        this.clazzName = clazzName;
        this.tableName = tableName;
    }

    public HibernateConfig() {
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
