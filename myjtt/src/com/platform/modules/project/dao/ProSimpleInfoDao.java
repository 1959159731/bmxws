package com.platform.modules.project.dao;


import java.util.List;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 项目基本信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-06
 */
@MyBatisDao
public interface ProSimpleInfoDao extends CrudDao<ProSimpleInfo> {
	
	
	/**
	 * 查找所有项目名称
	 * @param pSimpleInfo
	 * @return
	 */
	List<ProSimpleInfo> findAllProjectNames(ProSimpleInfo proSimpleInfo);
	
	
	/**
	 * 首页统计安全信息
	 * @return
	 */
	List<ProSimpleInfo> selectSafeReportInfo();
	
	
	/**
	 * 首页质量信息一览表
	 * @return
	 */
	List<ProSimpleInfo> selectQualityReportInfo();
}