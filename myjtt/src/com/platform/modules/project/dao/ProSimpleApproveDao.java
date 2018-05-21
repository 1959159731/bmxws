package com.platform.modules.project.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.project.entity.ProSimpleApprove;

/**
 * 项目基本信息-审批信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-06
 */
@MyBatisDao
public interface ProSimpleApproveDao extends CrudDao<ProSimpleApprove> {
	
	/**
	 * 根据项目ID修改审批信息
	 * @param proSimpleApprove
	 * @return
	 */
	int updateByProjectId(ProSimpleApprove proSimpleApprove);
}