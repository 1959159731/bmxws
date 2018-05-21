package com.platform.modules.quality.dao;

import java.util.List;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualityConstructionInfo;

/**
 * 施工单位信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-09
 */
@MyBatisDao
public interface QualityConstructionInfoDao extends CrudDao<QualityConstructionInfo> {

	
	// 查找所有施工单位名称
	List<String> findAllCons();
	
}