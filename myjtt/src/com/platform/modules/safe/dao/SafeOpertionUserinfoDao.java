package com.platform.modules.safe.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.safe.entity.SafeOpertionUserinfo;

/**
 * 特种、专用设备操作人员信息DAO接口
 * @author Mr.Jia
 * @version 2017-11-28
 */
@MyBatisDao
public interface SafeOpertionUserinfoDao extends CrudDao<SafeOpertionUserinfo> {
	
}