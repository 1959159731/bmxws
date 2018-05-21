package com.platform.modules.project.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.project.entity.ProSimpleBridgeTunnel;

/**
 * 项目桥梁、隧道信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-06
 */
@MyBatisDao
public interface ProSimpleBridgeTunnelDao extends CrudDao<ProSimpleBridgeTunnel> {
	
	/**
	 * 根据项目ID修改桥梁隧道信息
	 * @param proSimpleBridgeTunnel
	 * @return
	 */
	int updateByProjectId(ProSimpleBridgeTunnel proSimpleBridgeTunnel);
}