package com.fighter.ace.cms.action.admin.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by hanebert on 16/7/10.
 */
@Controller
public class FileDownloadAct {

    @RequestMapping("/common/o_download_file.do")
    public void downFile(String filePath , HttpServletRequest request,HttpServletResponse response) {
        String filepath = request.getRealPath("/") + filePath;
        File file = new File(filepath);
        if (!file.exists()) {
            return;
        }

        response.reset();// 不加这一句的话会出现下载错误
        response.setHeader("Content-disposition", "attachment; filename=" + file.getName() + "");// 设定输出文件头
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/vnd.ms-pki.stl");

        try {
            ServletOutputStream out = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(4096);

            byte[] cache = new byte[4096];
            for (int offset = fis.read(cache); offset != -1; offset = fis.read(cache)) {
                byteOutputStream.write(cache, 0, offset);
            }

            byte[] bt = null;
            bt = byteOutputStream.toByteArray();
            out.write(bt);
            out.flush();
            out.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
