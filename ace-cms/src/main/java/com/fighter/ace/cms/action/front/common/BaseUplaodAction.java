package com.fighter.ace.cms.action.front.common;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hanebert on 18/11/4.
 */
public class BaseUplaodAction {

    private String allowSuffix = "jpg,png,gif,jpeg,stl";//允许文件格式
    private long allowSize = 50L;//允许文件大小
    private String fileName;
    private String[] fileNames;
    private String origName;

    public String getAllowSuffix() {
        return allowSuffix;
    }

    public void setAllowSuffix(String allowSuffix) {
        this.allowSuffix = allowSuffix;
    }

    public long getAllowSize() {
        return allowSize * 1024 * 1024;
    }

    public void setAllowSize(long allowSize) {
        this.allowSize = allowSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getFileNames() {
        return fileNames;
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }

    public String getOrigName() {
        return origName;
    }

    public void setOrigName(String origName) {
        this.origName = origName;
    }

    /**
     * <p class="detail">
     * 功能：重新命名文件
     * </p>
     *
     * @return
     * @author wangsheng
     * @date 2014年9月25日
     */
    private String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }

    /**
     * <p class="detail">
     * 功能：文件上传
     * </p>
     *
     * @param files
     * @param destDir
     * @throws Exception
     * @author wangsheng
     * @date 2014年9月25日
     */
    public void uploads(MultipartFile[] files, String destDir, HttpServletRequest request) throws Exception {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        try {
            fileNames = new String[files.length];
            int index = 0;
            for (MultipartFile file : files) {
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                int length = getAllowSuffix().indexOf(suffix);
                if (length == -1) {
                    throw new Exception("请上传允许格式的文件");
                }
                if (file.getSize() > getAllowSize()) {
                    throw new Exception("您上传的文件大小已经超出范围");
                }
                String realPath = request.getSession().getServletContext().getRealPath("/");
                File destFile = new File(realPath + destDir);
                if (!destFile.exists()) {
                    destFile.mkdirs();
                }
                String fileNameNew = getFileNameNew() + "." + suffix;//
                File f = new File(destFile.getAbsoluteFile() + "\\" + fileNameNew);
                file.transferTo(f);
                f.createNewFile();
                origName = fileNameNew;
                fileNames[index++] = basePath + destDir + fileNameNew;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * <p class="detail">
     * 功能：文件上传
     * </p>
     *
     * @param file
     * @param destDir
     * @throws Exception
     * @author wangsheng
     * @date 2014年9月25日
     */
    public void upload(MultipartFile file, String destDir, HttpServletRequest request) throws Exception {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        try {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            int length = getAllowSuffix().indexOf(suffix);
            if (length == -1) {
                throw new Exception("请上传允许格式的文件");
            }
            if (file.getSize() > getAllowSize()) {
                throw new Exception("您上传的文件大小已经超出范围");
            }

            //String realPath = request.getSession().getServletContext().getRealPath("/");
            //File destFile = new File(realPath + destDir);
            File destFile = new File(destDir);
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            String fileNameNew = getFileNameNew() + "." + suffix;
            File f = new File(destFile.getAbsoluteFile() + "/" + fileNameNew);
            file.transferTo(f);
            f.createNewFile();
            fileName = basePath + destDir + fileNameNew;
            origName = fileNameNew;
        } catch (Exception e) {
            throw e;
        }
    }

}
