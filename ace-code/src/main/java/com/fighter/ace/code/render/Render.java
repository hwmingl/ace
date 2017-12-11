package com.fighter.ace.code.render;



/**
 * Created by hanebert on 17/10/31.
 */
public interface Render {

    void setHashKey(String hashKey);

    void render(RenderClass target, String template, String outpath) throws RenderException;

}
