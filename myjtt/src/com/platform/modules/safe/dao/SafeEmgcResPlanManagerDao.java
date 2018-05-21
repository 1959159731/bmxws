package com.platform.modules.safe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.safe.entity.SafeEmgcResPlanManager;

/**
 * 应急救援预案管理DAO接口
 * @author Mr.Jia
 * @version 2017-11-22
 */
@MyBatisDao
public interface SafeEmgcResPlanManagerDao extends CrudDao<SafeEmgcResPlanManager> {

	/**
	 * 根据项目名称获取合同编号
	 * @param projectName 
	 * @return List<String>
	 */
	List<String> findContractLabel(@Param("projectId")String projectId);

	
}