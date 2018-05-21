package com.platform.modules.project.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.project.entity.ProSimpleSupervision;

/**
 * 项目施工、监理单位信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-06
 */
@MyBatisDao
public interface ProSimpleSupervisionDao extends CrudDao<ProSimpleSupervision> {
	
	/**
	 * 根据项目ID修改施工、监理单位信息
	 * @param proSimpleSupervision
	 * @return
	 */
	int updateByProjectId(ProSimpleSupervision proSimpleSupervision);
	
}