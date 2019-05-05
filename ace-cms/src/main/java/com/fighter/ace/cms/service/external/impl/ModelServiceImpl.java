package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.CategoryDao;
import com.fighter.ace.cms.dao.external.ModelDao;
import com.fighter.ace.cms.entity.external.Category;
import com.fighter.ace.cms.entity.external.Model;
import com.fighter.ace.cms.service.external.ModelService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.google.common.base.Preconditions;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanebert on 16/6/4.
 */
@Service(value = "modelService")
public class ModelServiceImpl implements ModelService {

    private static final Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public void createModel(Model model) {
        Preconditions.checkNotNull(model, "modelDto is null");
        try{
            modelDao.insert(model);
            log.info("create model id={}", model.getId());
        }catch (Exception e){
            log.error("create error",e);
            throw new BizException("create error",e);
        }
    }

    @Override
    public Model getById(Long id) {
        Preconditions.checkArgument(id != 0, "id is invalid");
        return modelDao.getById(id);
    }

    @Override
    public void update(Model model) {
        modelDao.update(model);
    }

    @Override
    public void deleteById(Long id) {
        try{
            Model model = new Model();
            model.setId(Long.valueOf(id));
            model.setStatus(-1);
            modelDao.update(model);
        }catch (Exception e){
            e.printStackTrace();
            log.error("deleteById error",e);
            throw new BizException("deleteById error",e);
        }
    }

    @Override
    public void deleteByIds(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids is null");
        try{
            modelDao.batchDelete(ids);
            log.info("delete model ids={}", ids);
        }catch (Exception e){
            e.printStackTrace();
            log.error("deleteByIds error",e);
            throw new BizException("deleteByIds error",e);
        }
    }


    @Override
    public void approveByIds(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids is null");
        try{
            modelDao.approveByIds(ids);
            log.info("delete model ids={}", ids);
        }catch (Exception e){
            e.printStackTrace();
            log.error("deleteByIds error",e);
            throw new BizException("deleteByIds error",e);
        }
    }

    @Override
    public PageBean findList(PageParam pageParam, String name, Long categoryId,
                             String memberName,Integer queryOrderBy,Integer status) {
        try{
            Map<String,Object> paramMap = new HashMap<>();
            if (StringUtils.isNotBlank(name)){
                paramMap.put("name","%"+name+"%");
            }
            if (StringUtils.isNotBlank(memberName)){
                paramMap.put("memberName","%"+memberName+"%");
            }
            if (null != categoryId && categoryId > -1){
                List<Long> ids = getChildIdList(categoryId);
                paramMap.put("categoryIds",ids);
            }
            paramMap.put("queryOrderBy",queryOrderBy);
            paramMap.put("status",status);
            return modelDao.listPage(pageParam,paramMap);
        }catch (Exception e){
            e.printStackTrace();
            log.error("findList error", e);
            throw new BizException("findList error",e);
        }
    }

    @Override
    public PageBean findListByMemberId(PageParam pageParam, Map<String,Object> paramMap) {
        try{
            return modelDao.listPage(pageParam,paramMap);
        }catch (Exception e){
            log.error("findList error", e);
            throw new BizException("findList error",e);
        }
    }

    private List<Long> getChildIdList(Long parentId){
        List<Long> ids = new ArrayList<>();
        try{
            List<Category> categoryList = categoryDao.findListByParentId(parentId);
            if (null != categoryList && !categoryList.isEmpty()){
                for (Category category : categoryList){
                    ids.add(category.getId());
                }
            }else {
                ids.add(parentId);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("getChildIdList error", e);
            throw new BizException("getChildIdList error",e);
        }
        return ids;
    }
}
