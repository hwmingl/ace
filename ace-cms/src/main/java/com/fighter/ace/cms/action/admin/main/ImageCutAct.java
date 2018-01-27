package com.fighter.ace.cms.action.admin.main;


import com.fighter.ace.framework.common.image.ImageScale;
import com.fighter.ace.framework.common.upload.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by hanebert on 16/6/4.
 */
@Controller
public class ImageCutAct {

    private static final Logger log = LoggerFactory.getLogger(ImageCutAct.class);
    /**
     * 图片选择页面
     */
    public static final String IMAGE_SELECT_RESULT = "common/image_area_select";
    /**
     * 图片裁剪完成页面
     */
    public static final String IMAGE_CUTED = "common/image_cuted";
    /**
     * 错误信息参数
     */
    public static final String ERROR = "error";

    @RequestMapping("/common/v_image_area_select.do")
    public String imageAreaSelect(String uploadBase, String imgSrcPath,
                                  Integer zoomWidth, Integer zoomHeight, Integer uploadNum,
                                  HttpServletRequest request, ModelMap model) {
        model.addAttribute("uploadBase", uploadBase);
        model.addAttribute("imgSrcPath", imgSrcPath);
        model.addAttribute("zoomWidth", zoomWidth);
        model.addAttribute("zoomHeight", zoomHeight);
        model.addAttribute("uploadNum", uploadNum);
        return IMAGE_SELECT_RESULT;
    }

    @RequestMapping("/common/o_image_cut.do")
    public String imageCut(String imgSrcPath, Integer imgTop, Integer imgLeft,
                           Integer imgWidth, Integer imgHeight, Integer reMinWidth,
                           Integer reMinHeight, Float imgScale, Integer uploadNum,
                           HttpServletRequest request, ModelMap model) {
        try {
            if (imgWidth > 0) {
                String ctx = request.getContextPath();
                imgSrcPath = imgSrcPath.substring(ctx.length());
                File file = fileRepository.retrieve(imgSrcPath);
                imageScale.resizeFix(file, file, reMinWidth, reMinHeight,
                        getLen(imgTop, imgScale),
                        getLen(imgLeft, imgScale), getLen(imgWidth,
                                imgScale), getLen(imgHeight, imgScale));
            } else {
                String ctx = request.getContextPath();
                imgSrcPath = imgSrcPath.substring(ctx.length());
                File file = fileRepository.retrieve(imgSrcPath);
                imageScale.resizeFix(file, file, reMinWidth, reMinHeight);
            }
            model.addAttribute("uploadNum", uploadNum);
        } catch (Exception e) {
            log.error("cut image error", e);
            model.addAttribute(ERROR, e.getMessage());
        }
        return IMAGE_CUTED;
    }

    private int getLen(int len, float imgScale) {
        return Math.round(len / imgScale);
    }

    @Autowired
    private ImageScale imageScale;
    @Autowired
    private FileRepository fileRepository;

}
