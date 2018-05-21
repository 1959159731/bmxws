package com.platform.modules.safe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.safe.entity.SafeSpecEqui;

/**
 * 特种设备DAO接口
 * @author Mr.Jia
 * @version 2017-12-04
 */
@MyBatisDao
public interface SafeSpecEquiDao extends CrudDao<SafeSpecEqui> {
	
	List<String> findContractLabel(@Param("projectId") String projectId);
	
}