package com.platform.modules.quality.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualitySupervisonControlTable;

/**
 * 监理单位进场人员信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-09
 */
@MyBatisDao
public interface QualitySupervisonControlTableDao extends CrudDao<QualitySupervisonControlTable> {
	
}