package com.platform.modules.quality.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualityThirdCheckInfo;

/**
 * 第三方检测单位信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-10
 */
@MyBatisDao
public interface QualityThirdCheckInfoDao extends CrudDao<QualityThirdCheckInfo> {
	
}