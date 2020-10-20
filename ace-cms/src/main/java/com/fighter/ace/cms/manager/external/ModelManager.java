package com.fighter.ace.cms.manager.external;

import com.fighter.ace.cms.entity.external.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * 晋中3d
 * 子站读取主站数据
 *
 * Created by hanebert on 2020/1/5.
 */
@Component
public class ModelManager {

    private static final Logger log = LoggerFactory.getLogger(ModelManager.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据中心ID获取模型信息
     * @param id
     * @return
     */
    public Model getById(Long id){

        String sql = "SELECT id," +
                "            create_time as createTime," +
                "            name," +
                "            keyword," +
                "            category_id as categoryId," +
                "            pic_url as picUrl," +
                "            model_path as modelPath," +
                "            member_id as memberId," +
                "            coin," +
                "            point," +
                "            style," +
                "            size," +
                "            length," +
                "            width," +
                "            height," +
                "            down_count as downCount," +
                "            rank," +
                "            model_no as modelNo," +
                "            soft_version as softVersion," +
                "            mark," +
                "            status" +
                "        FROM t_model" +
                "        WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, Model.class, id);
    }


}
