package com.platform.modules.quality.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualitySupervisonInfo;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualitySupervisonInfoDao;

/**
 * 监理单位信息Service
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class QualitySupervisonInfoService extends CrudService<QualitySupervisonInfoDao, QualitySupervisonInfo> {

	@Resource
	private QualitySupervisonInfoDao qualitySupervisonInfoDao;
	
	public QualitySupervisonInfo get(String id) {
		return super.get(id);
	}
	
	public List<QualitySupervisonInfo> findList(QualitySupervisonInfo qualitySupervisonInfo) {
		qualitySupervisonInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualitySupervisonInfo);
	}
	
	public Page<QualitySupervisonInfo> findPage(Page<QualitySupervisonInfo> page, QualitySupervisonInfo qualitySupervisonInfo) {
		qualitySupervisonInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualitySupervisonInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(QualitySupervisonInfo qualitySupervisonInfo) {
		super.save(qualitySupervisonInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualitySupervisonInfo qualitySupervisonInfo) {
		super.delete(qualitySupervisonInfo);
	}
	
	
	public List<String> findAllCons() {
		return qualitySupervisonInfoDao.findAllCons();
	}
	
	public List<String> findSupervisonCompanyName(String projectId) {
		return qualitySupervisonInfoDao.findSupervisonCompanyName(projectId);
	}
	
}