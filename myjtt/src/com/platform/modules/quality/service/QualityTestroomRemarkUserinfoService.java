package com.platform.modules.quality.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualityTestroomRemarkUserinfo;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualityTestroomRemarkUserinfoDao;

/**
 * 试验室人员备案信息Service
 * @author Mr.Jia
 * @version 2017-12-13
 */
@Service
@Transactional(readOnly = true)
public class QualityTestroomRemarkUserinfoService extends CrudService<QualityTestroomRemarkUserinfoDao, QualityTestroomRemarkUserinfo> {

	@Resource
	private QualityTestroomRemarkUserinfoDao qualityTestroomRemarkUserinfoDao;
	
	public QualityTestroomRemarkUserinfo get(String id) {
		return super.get(id);
	}
	
	public List<QualityTestroomRemarkUserinfo> findList(QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo) {
		qualityTestroomRemarkUserinfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualityTestroomRemarkUserinfo);
	}
	
	public Page<QualityTestroomRemarkUserinfo> findPage(Page<QualityTestroomRemarkUserinfo> page, QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo) {
		qualityTestroomRemarkUserinfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualityTestroomRemarkUserinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo) {
		super.save(qualityTestroomRemarkUserinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo) {
		super.delete(qualityTestroomRemarkUserinfo);
	}
	
	@Transactional(readOnly = false)
	public int deleteByRemarkId(String remarkId) {
		return qualityTestroomRemarkUserinfoDao.deleteByRemarkId(remarkId);
	}
}