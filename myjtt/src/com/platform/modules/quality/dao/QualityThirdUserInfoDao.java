package com.platform.modules.quality.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualityThirdUserInfo;

/**
 * 检测单位工作内容汇总信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-11
 */
@MyBatisDao
public interface QualityThirdUserInfoDao extends CrudDao<QualityThirdUserInfo> {
	
}