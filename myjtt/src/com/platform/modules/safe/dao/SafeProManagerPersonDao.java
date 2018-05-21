package com.platform.modules.safe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.safe.entity.SafeProManagerPerson;

/**
 * 安全生产管理人员 DAO接口
 * @author Mr.Jia
 * @version 2017-11-26
 */
@MyBatisDao
public interface SafeProManagerPersonDao extends CrudDao<SafeProManagerPerson> {

	List<String> findContractLabel(@Param("projectId")String projectId);

}