package com.fighter.ace.cms.action.admin.external;

import com.fighter.ace.cms.entity.external.Article;
import com.fighter.ace.cms.entity.external.News;
import com.fighter.ace.cms.service.external.ArticleService;
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
 * Created by hanebert on 18/1/28.
 */
@Controller
public class ArticleAct {

    private static final Logger log = LoggerFactory.getLogger(ArticleAct.class);

    private static final String TITLE_SAVED = "新增文章";
    private static final String TITLE_UPDATED = "修改文章";
    private static final String TITLE_DELETED = "删除文章";
    private static final String TITLE_CHECKED = "审核文章";

    public static final int PAGESIZE = 20;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CmsLogService cmsLogService;

    @RequestMapping("/article/v_list.do")
    public String getArticleList(String pageNo, String title, String author, String recommend, ModelMap modelMap) {
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
            PageBean pageBean = articleService.getListPage(pageParam, map);
            modelMap.addAttribute("pageBean", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getArticleList error", e);
        }
        return "article/list";
    }

    @RequestMapping("/article/v_add.do")
    public String toAdd() {
        return "article/add";
    }

    @RequestMapping("/article/o_save.do")
    public String saveArticle(String title, Integer type, Integer style,String summary, String content, String source, String author,
                           String recommend, String typeImg, HttpServletRequest request) {
        Article n = new Article();
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
        Long id = articleService.addArticle(n);
        //新增操作日志
        cmsLogService.record(request, TITLE_SAVED, "id=" + id + ";title=" + title);

        return "redirect:v_list.do";
    }

    @RequestMapping("/article/v_edit.do")
    public String toEdit(String id, String pageNo, HttpServletRequest req, HttpServletResponse res) {
        Article article = articleService.getById(Long.valueOf(id));
        req.setAttribute("article", article);
        req.setAttribute("id", Long.valueOf(id));
        req.setAttribute("pageNo", pageNo);
        return "article/edit";
    }

    @RequestMapping("/article/o_edit.do")
    public String updateArticle(String pageNo, String id, String title, Integer type, Integer style, String summary, String content, String source, String author, String recommend, String typeImg, HttpServletRequest request) {

        try {
            Article n = new Article();
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
            articleService.updateArticle(n);

            //新增操作日志
            cmsLogService.record(request, TITLE_UPDATED, "id=" + id + ";title=" + title);
        } catch (Exception e) {
            log.error("updateNews error", e);
        }

        return "redirect:v_list.do?pageNo=" + pageNo;
    }

    @RequestMapping("/article/delete.do")
    public String deleteArticle(String id, String pageNo, HttpServletRequest request) {
        articleService.deleteArticle(Integer.parseInt(id));
        //新增操作日志
        cmsLogService.record(request, TITLE_DELETED, "id=" + id);
        return "redirect:v_list.do?pageNo=" + pageNo;
    }

    @RequestMapping("/article/batchDelete.do")
    public String batchDeleteArticle(int[] ids, String pageNo, HttpServletRequest request) {
        articleService.batchDeleteArticle(ids);
        //新增操作日志
        for (int i = 0; i < ids.length; i++) {
            cmsLogService.record(request, TITLE_DELETED, "id=" + ids[i]);
        }
        return "redirect:v_list.do?pageNo=" + pageNo;
    }

    @RequestMapping("/article/check.do")
    public String checkArticle(int[] ids, String pageNo, HttpServletRequest request) {
        try {
            articleService.checkArticle(ids);
            //新增操作日志
            for (int i = 0; i < ids.length; i++) {
                cmsLogService.record(request, TITLE_CHECKED, "id=" + ids[i]);
            }
        }catch (Exception e){
            log.error("checkArticle error",e);
        }
        return "redirect:v_list.do?pageNo=" + pageNo;
    }

}
