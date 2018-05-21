package com.platform.modules.safe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.safe.entity.SafeSpecOperPerson;

/**
 * 特种作业人员台帐DAO接口
 * @author Mr.Jia
 * @version 2017-11-26
 */
@MyBatisDao
public interface SafeSpecOperPersonDao extends CrudDao<SafeSpecOperPerson> {

	List<String> findContractLabel(@Param("projectId")String projectId);

}