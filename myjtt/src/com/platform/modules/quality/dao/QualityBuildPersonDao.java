package com.platform.modules.quality.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualityBuildPerson;

/**
 * 建设单位管理人员DAO接口
 * @author Mr.Jia
 * @version 2017-12-09
 */
@MyBatisDao
public interface QualityBuildPersonDao extends CrudDao<QualityBuildPerson> {
	
}