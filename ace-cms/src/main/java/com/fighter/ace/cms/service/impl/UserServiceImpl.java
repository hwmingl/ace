package com.fighter.ace.cms.service.impl;

import com.fighter.ace.cms.dao.main.UserDao;
import com.fighter.ace.cms.entity.main.User;
import com.fighter.ace.framework.common.exceptions.BizException;
import com.fighter.ace.cms.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hanebert on 17/9/22.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User getByUserName(String userName) throws BizException {
        Map<String,Object> params = Maps.newHashMap();
        params.put("username",userName);
        return userDao.getBy(params);
    }
}
