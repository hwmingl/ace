package com.fighter.ace.framework.directive;

import com.fighter.ace.framework.utils.Base64Util;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by hanebert on 2019/3/20.
 */
public class ImageDirective implements TemplateDirectiveModel {

    private String prefix;

    public static final String PARAM_URL = "url";

    @SuppressWarnings("unchecked")
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        String url = DirectiveUtils.getString(PARAM_URL, params);

        String data = null;
        try {
            data = Base64Util.encrypt(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String filePath = URLEncoder.encode(data, "UTF-8");
        env.getOut().append(prefix + filePath);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
