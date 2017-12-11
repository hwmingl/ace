package com.fighter.ace.code.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;

/**
 * Created by hanebert on 17/10/26.
 */
public class XMLHelper {

    private static Document doc;

    public XMLHelper(String filepath) {
        if(filepath != null) {
            File file = new File(filepath);
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding("UTF-8");

            try {
                doc = saxReader.read(file);
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

    }

    public XMLHelper(InputStream is) {
        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding("UTF-8");

        try {
            doc = saxReader.read(is);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public Document getDocument(String filepath) {
        return doc;
    }

    public String selectSingleNodeText(String nodepath) {
        Node node = doc.selectSingleNode(nodepath);
        return node.getText();
    }

    public String selectSingleNodeAttributeValueByKey(String nodepath, String atrribute) {
        Element el = (Element)doc.selectObject(nodepath);
        return el.attributeValue(atrribute);
    }

    public String getBuildconfigValueByKey(String nodepath) {
        return this.selectSingleNodeAttributeValueByKey(nodepath, "value");
    }

    public String getBuildPropertyValueByName(String propertyName) {
        return this.getBuildconfigValueByKey("project/property[@name=\'" + propertyName + "\']");
    }

}
