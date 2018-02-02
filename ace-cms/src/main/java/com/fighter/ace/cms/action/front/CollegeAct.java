package com.fighter.ace.cms.action.front;

import com.fighter.ace.cms.entity.external.Article;
import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.cms.service.external.ArticleService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 18/1/20.
 */
@Controller
public class CollegeAct extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(CollegeAct.class);
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/college/index")
    public String collegeIndex(String pageNo,ModelMap model){

        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo),3);
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("status", 1);

            PageBean<Article> pageBean = articleService.getListPage(pageParam, paramMap);
            model.addAttribute("pageBean", pageBean);

            //热点新闻
            List<Article> hotList = articleService.findHotList(5);
            model.addAttribute("hotList", hotList);

            model.addAttribute("position","college");
        } catch (Exception e){
            log.error("collegeIndex error",e);
        }
        return "college/index";
    }

    @RequestMapping("/college/list")
    public String collegeList(String pageNo,Integer style,ModelMap model){
        try {
            PageParam pageParam = new PageParam(getPageNo(pageNo),5);
            Map<String,Object> paramMap = new HashMap<>();
            if (null != style){
                paramMap.put("style",style);
                model.addAttribute("style",style);
            }
            paramMap.put("status", 1);
            PageBean<Article> pageBean = articleService.getListPage(pageParam, paramMap);
            model.addAttribute("pageBean", pageBean);

            List<Article> hotList = articleService.findHotList(10);
            model.addAttribute("hotList", hotList);

            List<Article> recmmList = articleService.findHotList(5);
            model.addAttribute("recmmList", recmmList);

            model.addAttribute("position","college");
        } catch (Exception e){
            log.error("collegeList",e);
        }
        return "college/list";
    }


    @RequestMapping(value = "college/{id}",method = RequestMethod.GET)
    public String getDetail(@PathVariable("id") Long id,HttpServletRequest request, ModelMap model){

        try{
            List<Article> newsList = articleService.findHotList(10);
            model.addAttribute("hotNewsList", newsList);

            Article article = articleService.getById(id);
            model.addAttribute("article", article);

            Article countNews = new Article();
            countNews.setId(id);
            countNews.setViewCount(article.getViewCount() + 1);
            articleService.updateArticle(countNews);

            model.addAttribute("position","college");
        }catch (Exception e){
            log.error("getDetail error," + e.getMessage(),e);
        }
        return "college/detail";
    }

}
