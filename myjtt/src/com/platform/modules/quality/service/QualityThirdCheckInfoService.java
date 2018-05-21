package com.platform.modules.quality.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualityThirdCheckInfo;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualityThirdCheckInfoDao;

/**
 * 第三方检测单位信息Service
 * @author Mr.Jia
 * @version 2017-12-10
 */
@Service
@Transactional(readOnly = true)
public class QualityThirdCheckInfoService extends CrudService<QualityThirdCheckInfoDao, QualityThirdCheckInfo> {

	public QualityThirdCheckInfo get(String id) {
		return super.get(id);
	}
	
	public List<QualityThirdCheckInfo> findList(QualityThirdCheckInfo qualityThirdCheckInfo) {
		qualityThirdCheckInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualityThirdCheckInfo);
	}
	
	public Page<QualityThirdCheckInfo> findPage(Page<QualityThirdCheckInfo> page, QualityThirdCheckInfo qualityThirdCheckInfo) {
		qualityThirdCheckInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualityThirdCheckInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(QualityThirdCheckInfo qualityThirdCheckInfo) {
		super.save(qualityThirdCheckInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualityThirdCheckInfo qualityThirdCheckInfo) {
		super.delete(qualityThirdCheckInfo);
	}
	
}