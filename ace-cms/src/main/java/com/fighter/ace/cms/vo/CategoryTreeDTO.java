package com.fighter.ace.cms.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by hanebert on 16/6/10.
 */
@Data
public class CategoryTreeDTO {

    private Long id;
    private String name;
    private List<CategoryTreeDTO> childs;

}
