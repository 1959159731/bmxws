package com.platform.modules.wexin.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.wexin.entity.WxToken;

/**
 * weixin Token Dao
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface WxTokenDao extends CrudDao<WxToken> {

}
