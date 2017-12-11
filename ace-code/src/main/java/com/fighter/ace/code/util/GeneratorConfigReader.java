package com.fighter.ace.code.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by hanebert on 17/10/26.
 */
public class GeneratorConfigReader {

    private GeneratorConfigReader() {
    }

    public static XMLHelper getTemplateConfigFile() {
        try {
            return new XMLHelper(GeneratorConfigReader.class.getClass().getResourceAsStream("/config/templates.xml"));
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static XMLHelper getBuildConfigFile() {
        try {
            File file = ResourceUtils.getFile("classpath:settings.xml");
            return new XMLHelper(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
