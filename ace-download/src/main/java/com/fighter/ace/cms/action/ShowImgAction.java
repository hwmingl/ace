package com.fighter.ace.cms.action;

import com.fighter.ace.cms.util.Base64Util;
import com.fighter.ace.cms.util.FileConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanebert on 2019/3/20.
 */

@Controller
public class ShowImgAction {

    @Resource
    private FileConfig fileConfig;


    private static Map imageContentType = new HashMap<>();
    static {
        imageContentType.put("jpg","image/jpeg");
        imageContentType.put("jpeg","image/jpeg");
        imageContentType.put("png","image/png");
        imageContentType.put("tif","image/tiff");
        imageContentType.put("tiff","image/tiff");
        imageContentType.put("ico","image/x-icon");
        imageContentType.put("bmp","image/bmp");
        imageContentType.put("gif","image/gif");
    }

    /**
     * 显示图片
     * @param root
     * @param data
     * @param request
     * @param response
     */
    @RequestMapping(method = {RequestMethod.GET}, value = {"/showImg"})
    public void showImage(String root,String data,HttpServletRequest request,HttpServletResponse response){
        //response.setDateHeader("Expires", 0L);
        //response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        //response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        //response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //response.setContentType("image/jpeg/jpg/png/gif/bmp/tiff/svg");
        try {
            String filePath = Base64Util.desEncrypt(data);
            String url  = fileConfig.getImgRootPath() + filePath.replace("/","\\");
            //String url  = fileConfig.getImgRootPath() + filePath;
            System.out.println("Can't find file." + url);
            File file = new File(url);       //括号里参数为文件图片路径
            if(file.exists()){   //如果文件存在
                InputStream in = new FileInputStream(url);   //用该文件创建一个输入流
                OutputStream os = response.getOutputStream();  //创建输出流
                byte[] b = new byte[1024];
                while( in.read(b)!= -1){
                    os.write(b);
                }
                in.close();
                os.flush();
                os.close();
            }else {
                System.out.println("Can't find file." + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
