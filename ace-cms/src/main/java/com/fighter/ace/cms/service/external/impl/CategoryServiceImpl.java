package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.CategoryDao;
import com.fighter.ace.cms.entity.external.Category;
import com.fighter.ace.cms.service.external.CategoryService;
import com.fighter.ace.cms.vo.CategoryTreeDTO;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanebert on 16/5/28.
 */
@Service(value = "categoryService")
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findListByParentId(long parentId) {
        Preconditions.checkArgument(parentId != 0, "parentId is invalid");
        return categoryDao.findListByParentId(parentId);
    }

    @Override
    public List<CategoryTreeDTO> getCategoryTree() {
        List<CategoryTreeDTO> dtoList = new ArrayList<>();
        try{
            List<Category> categoryList = categoryDao.findListByParentId(-1L);
            if (null != categoryList && !categoryList.isEmpty()){
                for (Category category : categoryList){
                    CategoryTreeDTO treeDto = new CategoryTreeDTO();
                    treeDto.setId(category.getId());
                    treeDto.setName(category.getName());
                    //二级节点
                    List<Category> childs = categoryDao.findListByParentId(category.getId());
                    List<CategoryTreeDTO> subTreeList = new ArrayList<>();
                    if (null != childs && !childs.isEmpty()){
                        for (Category cate : childs){
                            CategoryTreeDTO subTreeDto = new CategoryTreeDTO();
                            subTreeDto.setId(cate.getId());
                            subTreeDto.setName(cate.getName());
                            subTreeList.add(subTreeDto);
                        }
                    }
                    treeDto.setChilds(subTreeList);
                    dtoList.add(treeDto);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("getCategoryTree error",e);
            throw new BizException("getCategoryTree error",e);
        }
        return dtoList;
    }

    @Override
    public Long createCategory(Category category) {
        Preconditions.checkNotNull(category, "categoryDto is null");
        Preconditions.checkArgument(category.getParentId() != 0, "parentId is invalid");
        Long categoryId = 0L;
        try{
            categoryId = categoryDao.insert(category);
            log.info("save category id={}",category.getId());
        }catch (Exception e){
            e.printStackTrace();
            log.error("createCategory error",e);
            throw new BizException("createCategory error",e);
        }
        return categoryId;
    }

    @Override
    public Category getById(Long id) {
        Preconditions.checkArgument(id != 0, "id is invalid");
        Category category = null;
        try{
            category = categoryDao.getById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("getById error",e);
            throw new BizException("getById error",e);
        }
        return categoryDao.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        Preconditions.checkArgument(id != 0, "id is invalid");
        try{
            Category category = new Category();
            category.setId(id);
            category.setStatus(-1);
            categoryDao.update(category);
            log.info("delete category id={}",id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("createCategory error",e);
            throw new BizException("createCategory error",e);
        }
    }

    @Override
    public void deleteByIds(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids is null");
        try{
            categoryDao.batchDelete(ids);
            log.info("delete category ids={}", ids);
        }catch (Exception e){
            e.printStackTrace();
            log.error("deleteByIds error",e);
            throw new BizException("deleteByIds error",e);
        }
    }

    @Override
    public void updateName(Long id, String name) {
        Preconditions.checkNotNull(name, "name is null");
        Preconditions.checkArgument(id != 0, "id is invalid");
        try{
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            category.setStatus(1);
            categoryDao.update(category);
            log.info("updateName category id={}",id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("updateName error",e);
            throw new BizException("updateName error",e);
        }
    }

    @Override
    public boolean isExist(Long parentId, String name) {
        boolean isExist = false;
        Preconditions.checkNotNull(name, "name is null");
        Preconditions.checkArgument(parentId != 0, "parentId is invalid");
        try{
            List<Category> categoryList = categoryDao.findListByName(parentId,name);
            if (null != categoryList && !categoryList.isEmpty()){
                isExist = true;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("isExist error",e);
            throw new BizException("isExist error",e);
        }
        return isExist;
    }


}
