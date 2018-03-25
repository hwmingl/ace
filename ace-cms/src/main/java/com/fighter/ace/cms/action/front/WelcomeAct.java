package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.cms.service.external.ModelService;
import com.fighter.ace.cms.service.external.NewsService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hanebert on 17/9/12.
 */
@Controller
public class WelcomeAct extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(WelcomeAct.class);

    @Autowired
    private ModelService modelService;

    @Autowired
    private NewsService newsService;



    @RequestMapping("/index.do")
    public String index(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap) {
        try {
            //推荐模型
            PageBean pageBean = getPageList(-1L , 5 , "1");
            modelMap.addAttribute("rankList",pageBean.getRecordList());


            //今日看点
            List<News> dailyList = getNewsByStyle(1, 2);
            modelMap.addAttribute("dailyList",dailyList);
            //设计前沿
            List<News> designList = getNewsByStyle(2,6);
            modelMap.addAttribute("designList",designList);

        } catch (Exception e){
            log.error("index error",e);
        }
        modelMap.addAttribute("position","index");
        return "index";
    }

    private PageBean getPageList(Long categoryId, Integer queryOrderBy,String pageNo) {
        PageParam pageParam = new PageParam(getPageNo(pageNo),16);
        return modelService.findList(pageParam,null,categoryId,null,queryOrderBy,1);
    }


    private List<News> getNewsByStyle(Integer style,Integer count) {
            return newsService.findListByStyle(style,count);
    }


}
