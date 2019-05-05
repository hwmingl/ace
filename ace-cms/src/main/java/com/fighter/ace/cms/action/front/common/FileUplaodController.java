package com.fighter.ace.cms.action.front.common;

import com.fighter.ace.cms.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hanebert on 18/11/4.
 */
@Controller
public class FileUplaodController extends BaseUplaodAction{

    private static final Logger logger = LoggerFactory.getLogger(FileUplaodController.class);

    @Resource
    private ConfigUtil configUtil;

    @RequestMapping(method = {RequestMethod.POST}, value = {"/webUploader"})
    @ResponseBody
    public String uploadHeadPic(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response){
        try {
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
            String dateTime = simpleDateFormat.format(new Date());
            String destDir =  "/u/cms/"+dateTime+"/";

            String osName = System.getProperty("os.name").toLowerCase();
            String newDestDir = "";
            if (osName.contains("windows")){
                newDestDir = configUtil.getUploadFilePath() + destDir.replace("/","\\");
            } else {
                newDestDir = configUtil.getUploadFilePath() + destDir;
            }

            super.upload(file, newDestDir,request);
            response.getWriter().print(destDir + super.getOrigName().replace(basePath,""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
