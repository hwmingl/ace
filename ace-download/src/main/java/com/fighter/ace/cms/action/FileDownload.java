package com.fighter.ace.cms.action;

import com.fighter.ace.cms.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by hanebert on 18/3/25.
 */
@Controller
public class FileDownload {

    private static final Logger log = LoggerFactory.getLogger(FileDownload.class);

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/model/index")
    public String downloadIndex(String uuid,HttpServletRequest request, ModelMap model){


        new Thread(new Runnable() {
            @Override
            public void run() {
                long n = 0L;
                while (true){
                    try {
                        redisTemplate.opsForValue().set("uuid_" + n, String.valueOf(n));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        System.out.println("uuid_" + n + "=" + redisTemplate.opsForValue().get("uuid_" + n));
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    n++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    if (n > 1000000000000000000L){
                        break;
                    }
                }
            }
        }).start();

        return "model/index";
    }

    @RequestMapping("/model/notfound")
    public String fileNotFound(String uuid,HttpServletRequest request, ModelMap model){

        return "model/fileNotFound";
    }

    @RequestMapping("/model/download/{uuid}")
    public ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid,HttpServletRequest request, ModelMap model){
        ///Users/hanebert/Downloads
        ByteArrayInputStream is = new ByteArrayInputStream(FileUtil.file2byte("/Users/hanebert/Downloads/晋中3D打印平台设计方案-初稿.pdf"));
        //设置缓存
        byte[] body = new byte[is.available()];
        try {
            is.read(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        String filename = null;
        try {
            filename = URLEncoder.encode("晋中3D打印平台设计方案-初稿.pdf", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.add("Content-Disposition", "attchement;filename*=UTF-8''"+filename);
        //设置请求状态
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }


}
