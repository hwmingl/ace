package com.fighter.ace.cms.service.external;


import com.fighter.ace.cms.entity.external.Category;
import com.fighter.ace.cms.vo.CategoryTreeDTO;

import java.util.List;

/**
 * Created by hanebert on 16/5/28.
 */
public interface CategoryService {

    List<Category> findListByParentId(long parentId);

    List<CategoryTreeDTO> getCategoryTree();

    Long createCategory(Category categoryDto);

    Category getById(Long id);

    void deleteById(Long id);

    void deleteByIds(Long[] ids);

    void updateName(Long id, String name);

    boolean isExist(Long parentId, String name);

}
