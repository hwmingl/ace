package com.fighter.ace.cms.action.admin.external;


import com.fighter.ace.cms.entity.external.Notice;
import com.fighter.ace.cms.service.external.NoticeService;
import com.fighter.ace.cms.service.main.CmsLogService;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.fighter.ace.framework.web.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ma Senyu on 16/6/26.
 */
@Controller
public class NoticeAct {

    private static final Logger log = LoggerFactory.getLogger(NoticeAct.class);

    private static final String TITLE_SAVED = "新增公告";
    private static final String TITLE_UPDATED = "修改公告";
    private static final String TITLE_DELETED = "删除公告";


    public static final int PAGESIZE = 30;
    @Autowired
    NoticeService noticeService;
    @Autowired
    CmsLogService cmsLogService;

    @RequestMapping("notice/v_list.do")
    public  String getNoticeList(HttpServletRequest request, ModelMap model,String pageNo){
        if (pageNo ==null||"".equals(pageNo)){
            pageNo="1";
        }
        String title = RequestUtils.getQueryParam(request, "title");
        PageParam pageParam = new PageParam(Integer.valueOf(pageNo),PAGESIZE);
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(title)){
            map.put("title", "%"+title.trim()+"%");
        }
        PageBean pageBean = null;
        try {
            pageBean=noticeService.getListPage(pageParam,map);
        }catch (Exception e){
            e.printStackTrace();
            log.error("getNewsList error",e);
        }
        log.info("Notice get list");
        model.addAttribute("title",title);
        model.addAttribute("pageBean", pageBean);
        return "notice/list";
    }


    @RequestMapping("/notice/add.do")
    public String add(){
        return "notice/add";
    }


    @RequestMapping("/notice/save.do")
    public String save(String title,String content, HttpServletRequest request, ModelMap model){
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        try {
            Long id = noticeService.addNotice(notice);
            //新增操作日志
            cmsLogService.record(request, TITLE_SAVED, "id=" + id + ";title=" + title);
        }catch (Exception e){
            e.printStackTrace();
            log.error("save notice error",e);
        }
        return "redirect:v_list.do";
    }

    @RequestMapping("/notice/view.do")
    public  String detail(String id, String pageNo ,String title, HttpServletRequest request ){
        Notice notice =noticeService.getById(Integer.parseInt(id));
        request.setAttribute("notice", notice);
        request.getSession().setAttribute("pageNo",pageNo);
        request.setAttribute("title",title);
        return "notice/detail";
    }


    //详情页
    @RequestMapping("/notice/edit.do")
    public  String toEdit(String id,String pageNo ,HttpServletRequest request){
        Notice notice =noticeService.getById(Integer.parseInt(id));
        request.setAttribute("notice", notice);
        request.setAttribute("id", id);
        request.setAttribute("pageNo", pageNo);
     return "notice/edit";
    }


    //修改公告
    @RequestMapping("/notice/update.do")
    public  String update(String id ,String pageNo ,String title,String content ,HttpServletRequest request){
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setId(Long.parseLong(id));
        noticeService.updateNotice(notice);
        cmsLogService.record(request, TITLE_UPDATED, "id=" + id + ";title=" + title);
        return   "redirect:v_list.do?pageNo=" +pageNo ;
    }
    //删除公告
    @RequestMapping("/notice/delete.do")
    public  String deleteById(String id ,String pageNo, HttpServletRequest request){
        noticeService.deleteNotice(Integer.parseInt(id));
        cmsLogService.record(request, TITLE_DELETED, "id=" + id );
        return   "redirect:v_list.do?pageNo=" +pageNo ;
    }
    // 批量删除公告
    @RequestMapping("/notice/batchDelete.do")
    public  String batchDelete(String pageNo,int [] ids , HttpServletRequest request){
        noticeService.batchDeleteNotice(ids);
        for (int i = 0; i < ids.length; i++) {
            cmsLogService.record(request, TITLE_DELETED, "id=" + ids[i]);
        }
        return "redirect:v_list.do?pageNo=" +pageNo ;
    }
    //返回列表
    @RequestMapping("notice/relist.do")
    public  String getNoticereList(String pageNo,HttpServletRequest request){
        String  title = RequestUtils.getQueryParam(request, "title");
        return "redirect:v_list.do?pageNo=" +pageNo+"&title="+title;
    }
}
