package com.platform.modules.quality.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualityConstructionControlTable;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualityConstructionControlTableDao;

/**
 * 施工单位履约对照Service
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Service
@Transactional(readOnly = true)
public class QualityConstructionControlTableService extends CrudService<QualityConstructionControlTableDao, QualityConstructionControlTable> {

	public QualityConstructionControlTable get(String id) {
		return super.get(id);
	}
	
	public List<QualityConstructionControlTable> findList(QualityConstructionControlTable qualityConstructionControlTable) {
		qualityConstructionControlTable.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualityConstructionControlTable);
	}
	
	public Page<QualityConstructionControlTable> findPage(Page<QualityConstructionControlTable> page, QualityConstructionControlTable qualityConstructionControlTable) {
		qualityConstructionControlTable.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualityConstructionControlTable);
	}
	
	@Transactional(readOnly = false)
	public void save(QualityConstructionControlTable qualityConstructionControlTable) {
		super.save(qualityConstructionControlTable);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualityConstructionControlTable qualityConstructionControlTable) {
		super.delete(qualityConstructionControlTable);
	}
	
}