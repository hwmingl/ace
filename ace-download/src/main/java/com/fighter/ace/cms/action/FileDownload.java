package com.fighter.ace.cms.action;

import com.alibaba.fastjson.JSONObject;
import com.fighter.ace.cms.mq.MessageSender;
import com.fighter.ace.cms.util.FileConfig;
import com.fighter.ace.cms.util.FileUtil;
import org.apache.activemq.command.ActiveMQDestination;
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
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by hanebert on 18/3/25.
 */
@Controller
public class FileDownload {

    private static final Logger log = LoggerFactory.getLogger(FileDownload.class);

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private FileConfig fileConfig;

    @Resource
    private MessageSender messageSender;
    @Resource(name = "defaultDestination")
    private Destination queueDestination;

    @RequestMapping("/file/{uuid}")
    public String downloadIndex(@PathVariable("uuid") String uuid,HttpServletRequest request, ModelMap model){

        //下载测试
        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("domain","jz3d");
        jsonObject.put("memberId",12);
        jsonObject.put("payType",1);
        jsonObject.put("modelId", 36056);
        jsonObject.put("fileName","/u/cms/201606/211326426kma.stl");
        redisTemplate.opsForValue().set(uuid, jsonObject.toString());
*/
        Object object = redisTemplate.opsForValue().get(uuid);
        if (null == object){
            return "model/fileNotFound";
        }
        String downId = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(downId,object);
        //下载id
        model.addAttribute("uuid",downId);

        return "model/index";
    }

    @RequestMapping("/file/upload")
    public String uploadView(HttpServletRequest request, ModelMap model){
        return "model/upload";
    }

    @RequestMapping("/file/404")
    public String notFound(@PathVariable("uuid") String uuid,HttpServletRequest request, ModelMap model){
        return "model/404";
    }

    @RequestMapping("/file/download/{uuid}")
    public ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid,HttpServletResponse response, ModelMap model){

        Object object = redisTemplate.opsForValue().get(uuid);
        try {
            if (null == object){
                response.sendRedirect("/cms/file/404");
            }
        } catch (Exception e){

        }
        //通知已下载,进行扣分操作
        JSONObject jsonObject = JSONObject.parseObject(object.toString());
        //ActiveMQDestination activeMQDestination = (ActiveMQDestination)queueDestination;
        //messageSender.sendTxtMsg(activeMQDestination.getCompositeDestinations()[0],jsonObject.toJSONString());

        String fileName = jsonObject.getString("fileName");
        String filePath = fileConfig.getPathPrefix() + fileName;
        String fileHeader = fileName.substring(fileName.lastIndexOf("/") + 1,fileName.length());

        ByteArrayInputStream is = new ByteArrayInputStream(FileUtil.file2byte(filePath));
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
            filename = URLEncoder.encode(fileHeader, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.add("Content-Disposition", "attchement;filename*=UTF-8''"+fileHeader);
        //设置请求状态
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }





}
