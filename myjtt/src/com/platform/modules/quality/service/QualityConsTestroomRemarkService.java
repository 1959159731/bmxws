package com.platform.modules.quality.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualityConsTestroomRemark;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualityConsTestroomRemarkDao;

/**
 * 中心试验室备案信息Service
 * @author Mr.Jia
 * @version 2017-12-13
 */
@Service
@Transactional(readOnly = true)
public class QualityConsTestroomRemarkService extends CrudService<QualityConsTestroomRemarkDao, QualityConsTestroomRemark> {

	public QualityConsTestroomRemark get(String id) {
		return super.get(id);
	}
	
	public List<QualityConsTestroomRemark> findList(QualityConsTestroomRemark qualityConsTestroomRemark) {
		qualityConsTestroomRemark.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualityConsTestroomRemark);
	}
	
	public Page<QualityConsTestroomRemark> findPage(Page<QualityConsTestroomRemark> page, QualityConsTestroomRemark qualityConsTestroomRemark) {
		qualityConsTestroomRemark.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualityConsTestroomRemark);
	}
	
	@Transactional(readOnly = false)
	public void save(QualityConsTestroomRemark qualityConsTestroomRemark) {
		super.save(qualityConsTestroomRemark);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualityConsTestroomRemark qualityConsTestroomRemark) {
		super.delete(qualityConsTestroomRemark);
	}
	
}