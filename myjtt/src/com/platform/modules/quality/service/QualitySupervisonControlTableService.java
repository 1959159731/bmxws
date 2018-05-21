package com.platform.modules.quality.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualitySupervisonControlTable;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualitySupervisonControlTableDao;

/**
 * 监理单位进场人员信息Service
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class QualitySupervisonControlTableService extends CrudService<QualitySupervisonControlTableDao, QualitySupervisonControlTable> {

	public QualitySupervisonControlTable get(String id) {
		return super.get(id);
	}
	
	public List<QualitySupervisonControlTable> findList(QualitySupervisonControlTable qualitySupervisonControlTable) {
		qualitySupervisonControlTable.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualitySupervisonControlTable);
	}
	
	public Page<QualitySupervisonControlTable> findPage(Page<QualitySupervisonControlTable> page, QualitySupervisonControlTable qualitySupervisonControlTable) {
		qualitySupervisonControlTable.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualitySupervisonControlTable);
	}
	
	@Transactional(readOnly = false)
	public void save(QualitySupervisonControlTable qualitySupervisonControlTable) {
		super.save(qualitySupervisonControlTable);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualitySupervisonControlTable qualitySupervisonControlTable) {
		super.delete(qualitySupervisonControlTable);
	}
	
}