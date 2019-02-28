package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.cms.entity.external.Notice;
import com.fighter.ace.cms.service.external.NewsService;
import com.fighter.ace.cms.service.external.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hanebert on 2019/2/13.
 */
@Controller
public class NoticeAct extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(NoticeAct.class);

    @Autowired
    private NewsService newsService;
    @Resource
    private NoticeService noticeService;



    @RequestMapping(value = "/notice/{id}",method = RequestMethod.GET)
    public String getDetail(@PathVariable("id") Integer id,HttpServletRequest request, ModelMap model){

        try{
            List<News> newsList = newsService.findHotList(10);
            model.addAttribute("hotNewsList", newsList);

            Notice notice = noticeService.getById(id);
            model.addAttribute("notice", notice);

            model.addAttribute("position","news");
        }catch (Exception e){
            log.error("getDetail error," + e.getMessage(),e);
        }
        return "notice/detail";
    }



}
