package com.platform.modules.quality.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualityTestroomRemark;

/**
 * 中心试验室备案信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-13
 */
@MyBatisDao
public interface QualityTestroomRemarkDao extends CrudDao<QualityTestroomRemark> {
	
}