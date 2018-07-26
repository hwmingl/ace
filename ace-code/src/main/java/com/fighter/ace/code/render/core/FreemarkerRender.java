package com.fighter.ace.code.render.core;


import com.fighter.ace.code.render.Render;
import com.fighter.ace.code.render.RenderClass;
import com.fighter.ace.code.render.RenderException;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

/**
 * Created by hanebert on 17/10/31.
 */
public class FreemarkerRender implements Render {

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerRender.class);

    private Configuration templateConfig;
    private String hashKey = null;

    public FreemarkerRender() {
        this.initialize();
    }

    public void initialize() {
        try {
            if(this.templateConfig == null) {
                this.templateConfig = new Configuration();
                this.templateConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
                this.templateConfig.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
                this.templateConfig.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/"));
                this.templateConfig.setTemplateUpdateDelay(1200);
                this.templateConfig.setDefaultEncoding("utf-8");
                this.templateConfig.setLocale(new Locale("zh_CN"));
                this.templateConfig.setNumberFormat("0.##########");
            }
        } catch (Exception var2) {
            ;
        }

    }

    public void render(RenderClass target, String template, String outPath) throws RenderException {
        if(this.hashKey == null) {
            System.err.println("Simple HashKey is not null.");
        }

        try {
            StringWriter e = new StringWriter();
            Template tl = this.templateConfig.getTemplate(template, this.templateConfig.getLocale());
            SimpleHash root = new SimpleHash();
            root.put(this.hashKey, target);
            tl.process(root, e);
            //FileUtil.createNewFile(outPath);
            //FileUtil.writeTofileWithUTF8(outPath, e.toString());
            logger.info("生成文件" + outPath + "成功!");
        } catch (IOException var7) {
            var7.printStackTrace();
        } catch (TemplateException var8) {
            var8.printStackTrace();
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }

    public String getHashKey() {
        return this.hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }
}
