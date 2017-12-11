package com.fighter.ace.code.render;


/**
 * Created by hanebert on 17/10/31.
 */
public abstract class AbstractRender implements RenderClass {
    protected String packages;
    protected String clazzName;

    public AbstractRender() {
    }

    public String getPackages() {
        return this.packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getClazzName() {
        return this.clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }
}
