package com.fighter.ace.cms.service;

import com.fighter.ace.cms.entity.main.User;
import com.fighter.ace.framework.common.exceptions.BizException;

/**
 * Created by hanebert on 17/9/22.
 */
public interface UserService {

    User getByUserName(String userName) throws BizException;


}
