package com.platform.modules.quality.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualityThirdUserInfo;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualityThirdUserInfoDao;

/**
 * 检测单位工作内容汇总信息Service
 * @author Mr.Jia
 * @version 2017-12-11
 */
@Service
@Transactional(readOnly = true)
public class QualityThirdUserInfoService extends CrudService<QualityThirdUserInfoDao, QualityThirdUserInfo> {

	public QualityThirdUserInfo get(String id) {
		return super.get(id);
	}
	
	public List<QualityThirdUserInfo> findList(QualityThirdUserInfo qualityThirdUserInfo) {
		qualityThirdUserInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualityThirdUserInfo);
	}
	
	public Page<QualityThirdUserInfo> findPage(Page<QualityThirdUserInfo> page, QualityThirdUserInfo qualityThirdUserInfo) {
		qualityThirdUserInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualityThirdUserInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(QualityThirdUserInfo qualityThirdUserInfo) {
		super.save(qualityThirdUserInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualityThirdUserInfo qualityThirdUserInfo) {
		super.delete(qualityThirdUserInfo);
	}
	
}