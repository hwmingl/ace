package com.fighter.ace.code.render.pojo.hibernate;

import com.fighter.ace.code.render.pojo.Property;

/**
 * Created by hanebert on 17/10/31.
 */
public class HibernateConfigProperty extends Property {

    protected String sequence;
    protected String field;

    public HibernateConfigProperty() {
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSequence() {
        return this.sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

}
