package com.fighter.ace.cms.service.external.impl;

import com.fighter.ace.cms.dao.external.AddrDao;
import com.fighter.ace.cms.entity.external.Addr;
import com.fighter.ace.cms.service.external.AddrService;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.framework.common.page.PageBean;
import com.fighter.ace.framework.common.page.PageParam;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by hanebert on 2019/3/7.
 */
@Service(value = "addrService")
public class AddrServiceImpl implements AddrService {

    private static final Logger log = LoggerFactory.getLogger(AddrServiceImpl.class);

    @Resource
    private AddrDao addrDao;

    @Override
    public Long addAddr(Addr addr) {
        return addrDao.insert(addr);
    }

    @Override
    public PageBean getListPage(PageParam pageParam, Map<String, Object> paramMap) {
        try{
            return addrDao.listPage(pageParam, paramMap);
        }catch (Exception e){
            e.printStackTrace();
            log.error("findList error", e);
            throw new BizException("findList error",e);
        }
    }

    @Override
    public int deleteAddr(Long id) {
        Preconditions.checkArgument(id != 0, "id is invalid");
        try{
            log.info("delete category id={}", id);
            return addrDao.deleteById(Long.valueOf(id));
        }catch (Exception e){
            log.error("deleteById error",e);
            throw new BizException("deleteById error",e);
        }
    }

    @Override
    public Addr getById(Long id) {
        return addrDao.getById(id);
    }

    @Override
    public int updateAddr(Addr addr) {
        return addrDao.update(addr);
    }

    @Override
    public Addr getByDeft() {
        return addrDao.getByDeft();
    }
}
