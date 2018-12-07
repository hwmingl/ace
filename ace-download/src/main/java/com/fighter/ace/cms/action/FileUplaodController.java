package com.fighter.ace.cms.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(method = {RequestMethod.POST}, value = {"/webUploader"})
    @ResponseBody
    public String uploadHeadPic(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response){
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateTime = simpleDateFormat.format(new Date());

            super.upload(file, "/upload/user/"+dateTime+"/",request);
            response.getWriter().print(super.getFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
