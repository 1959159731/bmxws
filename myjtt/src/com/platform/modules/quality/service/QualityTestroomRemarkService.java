package com.platform.modules.quality.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualityTestroomRemark;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualityTestroomRemarkDao;

/**
 * 中心试验室备案信息Service
 * @author Mr.Jia
 * @version 2017-12-13
 */
@Service
@Transactional(readOnly = true)
public class QualityTestroomRemarkService extends CrudService<QualityTestroomRemarkDao, QualityTestroomRemark> {

	public QualityTestroomRemark get(String id) {
		return super.get(id);
	}
	
	public List<QualityTestroomRemark> findList(QualityTestroomRemark qualityTestroomRemark) {
		qualityTestroomRemark.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualityTestroomRemark);
	}
	
	public Page<QualityTestroomRemark> findPage(Page<QualityTestroomRemark> page, QualityTestroomRemark qualityTestroomRemark) {
		qualityTestroomRemark.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualityTestroomRemark);
	}
	
	@Transactional(readOnly = false)
	public void save(QualityTestroomRemark qualityTestroomRemark) {
		super.save(qualityTestroomRemark);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualityTestroomRemark qualityTestroomRemark) {
		super.delete(qualityTestroomRemark);
	}
	
}