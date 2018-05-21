package com.platform.modules.quality.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualityConstructionControlTable;

/**
 * 施工单位履约对照DAO接口
 * @author Mr.Jia
 * @version 2017-12-09
 */
@MyBatisDao
public interface QualityConstructionControlTableDao extends CrudDao<QualityConstructionControlTable> {
	
}