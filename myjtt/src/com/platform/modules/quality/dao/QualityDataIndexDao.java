package com.platform.modules.quality.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualityDataIndex;

/**
 * 质量检测统计数据DAO接口
 * @author Mr.Jia
 * @version 2018-02-04
 */
@MyBatisDao
public interface QualityDataIndexDao extends CrudDao<QualityDataIndex> {
	
}