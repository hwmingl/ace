package com.fighter.ace.cms.action.admin.main;


import com.fighter.ace.cms.action.admin.common.AbstractUpload;
import com.fighter.ace.cms.entity.main.MarkConfig;
import com.fighter.ace.cms.service.main.MarkConfigService;
import com.fighter.ace.cms.util.CmsUtil;
import com.fighter.ace.framework.common.upload.FileRepository;
import com.fighter.ace.framework.web.WebErrors;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by hanebert on 16/6/4.
 */
@Controller
public class ImageUploadAct extends AbstractUpload {

    private static final Logger log = LoggerFactory.getLogger(ImageUploadAct.class);

    /**
     * 结果页
     */
    private static final String RESULT_PAGE = "common/iframe_upload";
    /**
     * 错误信息参数
     */
    public static final String ERROR = "error";

    @Autowired
    private MarkConfigService markConfigService;
    @Autowired
    private FileRepository fileRepository;

    @RequestMapping("/common/o_upload_image.do")
    public String execute(
            String filename,
            Integer uploadNum,
            Boolean mark,
            @RequestParam(value = "uploadFile", required = false) MultipartFile file,
            HttpServletRequest request, ModelMap model) {

        WebErrors errors = validate(filename, file, request);
        if (errors.hasErrors()) {
            model.addAttribute(ERROR, errors.getErrors().get(0));
            return RESULT_PAGE;
        }
        MarkConfig conf = markConfigService.getMarkConfig();//CmsUtil.getMarkConfig();
        if (mark == null) {
            mark = conf.getOn();
        }

        String origName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
        try {
            String fileUrl;
            String ctx = request.getContextPath();
            if (!StringUtils.isBlank(filename)) {
                filename = filename.substring(ctx.length());
                if (mark) {
                    File tempFile = mark(file, conf);
                    fileUrl = fileRepository.storeByFilename(filename, tempFile);
                    tempFile.delete();
                } else {
                    fileUrl = fileRepository.storeByFilename(filename, file);
                }
            } else {
                if (mark) {
                    File tempFile = mark(file, conf);
                    fileUrl = fileRepository.storeByExt(CmsUtil.getUploadPath(), ext, tempFile);
                    tempFile.delete();
                } else {
                    fileUrl = fileRepository.storeByExt(CmsUtil.getUploadPath(), ext, file);
                }
                // 加上部署路径
                fileUrl = ctx + fileUrl;
            }
            //fileMng.saveFileByPath(fileUrl, origName, false);
            model.addAttribute("uploadPath", fileUrl);
            model.addAttribute("uploadNum", uploadNum);
        } catch (IllegalStateException e) {
            model.addAttribute(ERROR, e.getMessage());
            log.error("upload file error!", e);
        } catch (IOException e) {
            model.addAttribute(ERROR, e.getMessage());
            log.error("upload file error!", e);
        } catch (Exception e) {
            model.addAttribute(ERROR, e.getMessage());
            log.error("upload file error!", e);
        }
        return RESULT_PAGE;
    }

}
