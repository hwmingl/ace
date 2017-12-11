package com.fighter.ace.code.render.pojo;

import com.fighter.ace.code.render.AbstractRender;

import java.util.List;

/**
 * Created by hanebert on 17/10/31.
 */
public class POJO extends AbstractRender {

    protected Property id;
    protected List<Property> properties;

    public POJO() {
    }

    public POJO(String className, String packageName) {
        this.setClazzName(className);
        this.setPackages(packageName);
    }

    public Property getId() {
        return this.id;
    }

    public void setId(Property id) {
        this.id = id;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}
