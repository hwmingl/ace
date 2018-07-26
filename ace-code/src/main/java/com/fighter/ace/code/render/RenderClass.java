package com.fighter.ace.code.render;

import java.util.List;

/**
 * Created by hanebert on 17/10/31.
 */
public interface RenderClass {

    String getPackages();

    String getClazzName();

    com.fighter.ace.code.render.pojo.Property getId();

    List<com.fighter.ace.code.render.pojo.Property> getProperties();

}
