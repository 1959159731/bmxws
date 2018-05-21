package com.platform.modules.quality.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualityBuildPerson;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualityBuildPersonDao;

/**
 * 建设单位管理人员Service
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class QualityBuildPersonService extends CrudService<QualityBuildPersonDao, QualityBuildPerson> {

	public QualityBuildPerson get(String id) {
		return super.get(id);
	}
	
	public List<QualityBuildPerson> findList(QualityBuildPerson qualityBuildPerson) {
		qualityBuildPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualityBuildPerson);
	}
	
	public Page<QualityBuildPerson> findPage(Page<QualityBuildPerson> page, QualityBuildPerson qualityBuildPerson) {
		qualityBuildPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualityBuildPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(QualityBuildPerson qualityBuildPerson) {
		super.save(qualityBuildPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualityBuildPerson qualityBuildPerson) {
		super.delete(qualityBuildPerson);
	}
	
}