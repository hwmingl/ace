package com.fighter.ace.cms.action.admin.common;

import com.alibaba.fastjson.JSONObject;

import com.fighter.ace.cms.util.CmsUtil;
import com.fighter.ace.framework.common.upload.FileRepository;
import com.fighter.ace.framework.web.ResponseUtils;
import com.fighter.ace.framework.web.WebErrors;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by hanebert on 16/7/10.
 */
@Controller
public class SwfUploadAct extends AbstractUpload{

    private static final Logger log = LoggerFactory.getLogger(SwfUploadAct.class);
    /**
     * 错误信息参数
     */
    public static final String ERROR = "error";

    @Autowired
    private FileRepository fileRepository;


    @RequestMapping(value = "/common/o_swfAttachsUpload.do", method = RequestMethod.POST)
    public void swfAttachsUpload(
            String root,
            Integer uploadNum,
            @RequestParam(value = "Filedata", required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {
        WebErrors errors = WebErrors.create(request);//validate(file.getOriginalFilename(), file, request);
        if (errors.hasErrors()) {
            model.addAttribute(ERROR, errors.getErrors().get(0));
        }
        String ctx = request.getContextPath();
        String origName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
        // TODO 检查允许上传的后缀
        String fileUrl="";
        try {
            fileUrl = fileRepository.storeByExt(CmsUtil.getUploadPath(), ext, file);
            // 加上部署路径
            //fileUrl = ctx + fileUrl;
            log.info("received file,and save as "+ fileUrl);
            //model.addAttribute("attachmentPath", fileUrl);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
            log.error("upload file error!", e);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        }
        JSONObject data=new JSONObject();
        data.put("attachUrl", fileUrl);
        data.put("attachName", origName);
        ResponseUtils.renderJson(response, data.toString());
    }

}
