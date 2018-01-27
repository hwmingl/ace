package com.fighter.ace.cms.action.admin.common;

import com.fighter.ace.cms.entity.main.MarkConfig;
import com.fighter.ace.framework.common.image.ImageScale;
import com.fighter.ace.framework.common.image.ImageUtils;
import com.fighter.ace.framework.common.web.springmvc.RealPathResolver;
import com.fighter.ace.framework.web.WebErrors;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by hanebert on 16/6/4.
 */
public class AbstractUpload {

    @Autowired
    protected RealPathResolver realPathResolver;
    @Autowired
    protected ImageScale imageScale;

    protected WebErrors validate(String filename, MultipartFile file,
                                 HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (file == null) {
            errors.addErrorCode("imageupload.error.noFileToUpload");
            return errors;
        }
        if (StringUtils.isBlank(filename)) {
            filename = file.getOriginalFilename();
        }
        String ext = FilenameUtils.getExtension(filename);
        if (!ImageUtils.isValidImageExt(ext)) {
            errors.addErrorCode("imageupload.error.notSupportExt", ext);
            return errors;
        }
        try {
            if (!ImageUtils.isImage(file.getInputStream())) {
                errors.addErrorCode("imageupload.error.notImage", ext);
                return errors;
            }
        } catch (IOException e) {
            errors.addErrorCode("imageupload.error.ioError", ext);
            return errors;
        }
        return errors;
    }

    protected File mark(MultipartFile file, MarkConfig conf) throws Exception {
        String path = System.getProperty("java.io.tmpdir");
        File tempFile = new File(path, String.valueOf(System
                .currentTimeMillis()));
        file.transferTo(tempFile);
        boolean imgMark = !StringUtils.isBlank(conf.getImagePath());
        if (imgMark) {
            File markImg = new File(realPathResolver.get(conf.getImagePath()));
            imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf
                    .getMinHeight(), conf.getPos(), conf.getOffsetX(), conf
                    .getOffsetY(), markImg);
        } else {
            imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf
                    .getMinHeight(), conf.getPos(), conf.getOffsetX(), conf
                    .getOffsetY(), conf.getContent(), Color.decode(conf
                    .getColor()), conf.getSize(), conf.getAlpha());
        }
        return tempFile;
    }

}
