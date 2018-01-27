package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.ActivationDao;
import com.fighter.ace.cms.entity.external.Activation;
import com.fighter.ace.cms.service.external.ActivationService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.google.common.base.Preconditions;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by hanebert on 16/6/28.
 */
@Service(value = "activationService")
public class ActivationServiceImpl implements ActivationService {

    private static final Logger log = LoggerFactory.getLogger(ActivationServiceImpl.class);

    @Autowired
    private ActivationDao activationDao;

    @Override
    public void save(Date expiredTime,Integer num,Integer point) {
        try{
            for (int i = 0; i < num; i++) {
                Activation activationCode = new Activation();
                activationCode.setExpiredTime(expiredTime);
                String code = genActiveCode().toUpperCase();
                activationCode.setCode(code);
                activationCode.setPoint(point);
                activationDao.insert(activationCode);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("save error",e);
            throw new BizException("save error",e);
        }
    }

    @Override
    public int deleteById(long id) {
        Preconditions.checkArgument(id != 0, "id is invalid");
        try{
            log.info("delete category id={}", id);
            return activationDao.deleteById(Long.valueOf(id));
        }catch (Exception e){
            e.printStackTrace();
            log.error("deleteById error",e);
            throw new BizException("deleteById error",e);
        }
    }

    @Override
    public int deleteByIds(long[] ids) {
        Preconditions.checkNotNull(ids, "ids is null");
        try{
            log.info("delete Activation code ids={}", ids);
            return activationDao.batchDelete(ids);
        }catch (Exception e){
            e.printStackTrace();
            log.error("deleteByIds error",e);
            throw new BizException("deleteByIds error",e);
        }
    }

    @Override
    public PageBean findList(PageParam pageParam, String code) {
        try{
            Map<String,Object> paramMap = new HashMap<>();
            if (StringUtils.isNotBlank(code)){
                paramMap.put("code","%"+code+"%");
            }
            return activationDao.listPage(pageParam, paramMap);
        }catch (Exception e){
            e.printStackTrace();
            log.error("findList error", e);
            throw new BizException("findList error",e);
        }
    }


    private String genCode(){
        String[] cgroup = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p",
                "q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            int randNum = random.nextInt(cgroup.length-1);
            stringBuffer.append(cgroup[randNum]);
        }
        return stringBuffer.toString();
    }

    public String genActiveCode(){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            stringBuffer.append(genCode());
            if (i < 4 - 1){
                stringBuffer.append("-");
            }
        }
        return stringBuffer.toString();
    }
}
