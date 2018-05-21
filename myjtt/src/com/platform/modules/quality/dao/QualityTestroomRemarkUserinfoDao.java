package com.platform.modules.quality.dao;

import org.apache.ibatis.annotations.Param;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualityTestroomRemarkUserinfo;

/**
 * 试验室人员备案信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-13
 */
@MyBatisDao
public interface QualityTestroomRemarkUserinfoDao extends CrudDao<QualityTestroomRemarkUserinfo> {

	int deleteByRemarkId(@Param("remarkId") String remarkId);
	
}