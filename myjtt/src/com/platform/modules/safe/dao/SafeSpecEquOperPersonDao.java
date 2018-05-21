package com.platform.modules.safe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.safe.entity.SafeSpecEquOperPerson;

/**
 * 特种设备操作人员DAO接口
 * @author Me.Jia
 * @version 2017-11-26
 */
@MyBatisDao
public interface SafeSpecEquOperPersonDao extends CrudDao<SafeSpecEquOperPerson> {


	List<String> findContractLabel(@Param("projectId") String projectId);

	
}