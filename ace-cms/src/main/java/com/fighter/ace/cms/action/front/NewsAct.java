package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.cms.service.external.NewsService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 18/1/20.
 */
@Controller
public class NewsAct extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(NewsAct.class);

    @Autowired
    private NewsService newsService;

    @RequestMapping("/news/index")
    public String newsIndex(String pageNo,Integer style,ModelMap model){

        try {
            //焦点新闻
            List<News> focusList = findFocusList();
            model.addAttribute("focus1",focusList.get(0));
            model.addAttribute("focus2",focusList.get(1));
            model.addAttribute("focus3",focusList.get(2));
            model.addAttribute("focus4",focusList.get(3));

            //分页新闻
            PageParam pageParam = new PageParam(getPageNo(pageNo),3);
            Map<String,Object> paramMap = new HashMap<>();
            if (null != style){
                paramMap.put("style",style);
                model.addAttribute("style",style);
            }
            paramMap.put("status", 1);
            PageBean<News> pageBean = newsService.getListPage(pageParam, paramMap);
            model.addAttribute("pageBean", pageBean);

            //热点新闻
            List<News> hotList = newsService.findHotList(5);
            model.addAttribute("hotList",hotList);
            //导航定位
            model.addAttribute("position","news");
        } catch (Exception e){
            log.error("newsIndex error",e);
        }
        return "news/index";
    }


    /**
     * 新闻详细
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "news/{id}",method = RequestMethod.GET)
    public String getDetail(@PathVariable("id") Long id,HttpServletRequest request, ModelMap model){

        try{
            List<News> newsList = newsService.findHotList(10);
            model.addAttribute("hotNewsList", newsList);

            News news = newsService.getById(id);
            model.addAttribute("news", news);

            News countNews = new News();
            countNews.setId(id);
            countNews.setViewCount(news.getViewCount() + 1);
            newsService.updateNews(countNews);

            model.addAttribute("position","news");
        }catch (Exception e){
            log.error("getDetail error," + e.getMessage(),e);
        }
        return "news/detail";
    }



    private List<News> findFocusList(){
        List<News> newsList = new ArrayList<>();
        try {
            PageParam pageParam = new PageParam(1,4);
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("status",1);
            paramMap.put("type",1);
            PageBean<News> pageBean = newsService.getListPage(pageParam, paramMap);
            newsList.addAll(pageBean.getRecordList());
            if (pageBean.getTotalCount() < 4) {
                for (int i = 0; i < 4-pageBean.getTotalCount(); i++) {
                    News news = new News();
                    news.setId(Long.valueOf(i));
                    news.setTitle("暂无焦点新闻");
                    news.setPicUrl("/jz3d/res/images/090246310906596.jpg");
                    newsList.add(news);
                }
            }

        }catch (Exception e){
            log.error("findFocusList error",e);
        }
        return newsList;
    }

}
