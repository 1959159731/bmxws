package com.platform.modules.quality.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualitySupervisonInfo;

/**
 * 监理单位信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-09
 */
@MyBatisDao
public interface QualitySupervisonInfoDao extends CrudDao<QualitySupervisonInfo> {

	// 查找所有监理单位名称
	List<String> findAllCons();
		
	
	List<String> findSupervisonCompanyName(@Param("projectId")String projectId);
}