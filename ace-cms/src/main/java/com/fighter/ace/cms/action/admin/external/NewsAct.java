package com.fighter.ace.cms.action.admin.external;

import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.cms.service.external.NewsService;
import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hxxing on 2016/5/28.
 */
@Controller
public class NewsAct {
    private static final Logger log = LoggerFactory.getLogger(NewsAct.class);

    private static final String TITLE_SAVED = "新增新闻";
    private static final String TITLE_UPDATED = "修改新闻";
    private static final String TITLE_DELETED = "删除新闻";
    private static final String TITLE_CHECKED = "审核新闻";

    public static final int PAGESIZE = 20;

    @Autowired
    NewsService newsService;
    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping("/news/v_list.do")
    public String getNewsList(String pageNo, String title, String author, String recommend, ModelMap modelMap) {
        if (pageNo == null || "".equals(pageNo)) {
            pageNo = "1";
        }
        try {
            PageParam pageParam = new PageParam(Integer.parseInt(pageNo), PAGESIZE);
            Map<String, Object> map = new HashMap<>();
            if (title != null && !"".equals(title)) {
                map.put("title", "%" + title + "%");
                modelMap.addAttribute("queryTitle", title);
            }
            if (author != null && !"".equals(author)) {
                map.put("author", "%" + author + "%");
                modelMap.addAttribute("queryAuthor", author);
            }
            if (Boolean.parseBoolean(recommend) == true) {
                map.put("recommend", 1);
                modelMap.addAttribute("queryRecommend", 1);
            }
            PageBean pageBean = newsService.getListPage(pageParam, map);
            modelMap.addAttribute("pageBean", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getNewsList error", e);
        }
        return "news/list";
    }

    @RequestMapping("/news/v_add.do")
    public String toAdd() {
        return "news/add";
    }

    @RequestMapping("/news/o_save.do")
    public String saveNews(String title, Integer type, Integer style,String summary, String content, String source, String author,
                           String recommend, String typeImg, HttpServletRequest request) {
        News n = new News();
        n.setTitle(title);
        n.setType(type);
        n.setStyle(style);
        n.setSummary(summary);
        n.setContent(content);
        n.setSource(source);
        n.setAuthor(author);
        n.setRecommend(Boolean.parseBoolean(recommend) ? 1 : 0);
        if (StringUtils.isNotBlank(typeImg)) {
            n.setPicUrl(typeImg);
        }
        long id = newsService.addNews(n);

        //新增操作日志
        cmsLogService.record(request, TITLE_SAVED, "id=" + id + ";title=" + title);

        return "redirect:v_list.do";
    }

    @RequestMapping("/news/v_edit.do")
    public String toEdit(String id, String pageNo, HttpServletRequest req, HttpServletResponse res) {
        News news = newsService.getById(Long.valueOf(id));
        req.setAttribute("news", news);
        req.setAttribute("id", Long.valueOf(id));
        req.setAttribute("pageNo", pageNo);
        return "news/edit";
    }

    @RequestMapping("/news/o_edit.do")
    public String updateNews(String pageNo, String id, String title, Integer type, Integer style, String summary, String content, String source, String author, String recommend, String typeImg, HttpServletRequest request) {

        try {
            News n = new News();
            n.setId(Long.parseLong(id));
            n.setTitle(title);
            n.setType(type);
            n.setStyle(style);
            n.setSummary(summary);
            n.setContent(content);
            n.setSource(source);
            n.setAuthor(author);
            n.setRecommend(Boolean.parseBoolean(recommend) ? 1 : 0);
            if (StringUtils.isNotBlank(typeImg)) {
                n.setPicUrl(typeImg);
            }
            newsService.updateNews(n);

            //新增操作日志
            cmsLogService.record(request, TITLE_UPDATED, "id=" + id + ";title=" + title);
        } catch (Exception e) {
            log.error("updateNews error", e);
        }

        return "redirect:v_list.do?pageNo=" + pageNo;
    }

    @RequestMapping("/news/delete.do")
    public String deleteNews(String id, String pageNo, HttpServletRequest request) {
        try {
            newsService.deleteNews(Integer.parseInt(id));
            //新增操作日志
            cmsLogService.record(request, TITLE_DELETED, "id=" + id);
        }catch (Exception e){
            log.error("deleteNews Error",e);
        }
        return "redirect:v_list.do?pageNo=" + pageNo;
    }

    @RequestMapping("/news/batchDelete.do")
    public String batchDeleteNews(int[] ids, String pageNo, HttpServletRequest request) {
        newsService.batchDeleteNews(ids);
        //新增操作日志
        for (int i = 0; i < ids.length; i++) {
            cmsLogService.record(request, TITLE_DELETED, "id=" + ids[i]);
        }
        return "redirect:v_list.do?pageNo=" + pageNo;
    }

    @RequestMapping("/news/check.do")
    public String checkNews(int[] ids, String pageNo, HttpServletRequest request) {
        try {
            newsService.checkNews(ids);
            //新增操作日志
            for (int i = 0; i < ids.length; i++) {
                cmsLogService.record(request, TITLE_CHECKED, "id=" + ids[i]);
            }
        }catch (Exception e){
            log.error("checkNews error",e);
        }
        return "redirect:v_list.do?pageNo=" + pageNo;
    }
}
