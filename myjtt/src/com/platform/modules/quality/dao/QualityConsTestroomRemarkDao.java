package com.platform.modules.quality.dao;

import com.platform.common.persistence.CrudDao;
import com.platform.common.persistence.annotation.MyBatisDao;
import com.platform.modules.quality.entity.QualityConsTestroomRemark;

/**
 * 施工单位试验室备案信息DAO接口
 * @author Mr.Jia
 * @version 2017-12-13
 */
@MyBatisDao
public interface QualityConsTestroomRemarkDao extends CrudDao<QualityConsTestroomRemark> {
	
}