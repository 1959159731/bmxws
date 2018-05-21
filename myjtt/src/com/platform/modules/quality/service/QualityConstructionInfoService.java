package com.platform.modules.quality.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualityConstructionInfo;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualityConstructionInfoDao;

/**
 * 施工单位信息Service
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class QualityConstructionInfoService extends CrudService<QualityConstructionInfoDao, QualityConstructionInfo> {

	@Resource
	private QualityConstructionInfoDao qualityConstructionInfoDao;
	
	public QualityConstructionInfo get(String id) {
		return super.get(id);
	}
	
	public List<QualityConstructionInfo> findList(QualityConstructionInfo qualityConstructionInfo) {
		qualityConstructionInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualityConstructionInfo);
	}
	
	public Page<QualityConstructionInfo> findPage(Page<QualityConstructionInfo> page, QualityConstructionInfo qualityConstructionInfo) {
		qualityConstructionInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualityConstructionInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(QualityConstructionInfo qualityConstructionInfo) {
		super.save(qualityConstructionInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualityConstructionInfo qualityConstructionInfo) {
		super.delete(qualityConstructionInfo);
	}
	
	public List<String> findAllCons() {
		return qualityConstructionInfoDao.findAllCons();
	}
	
}