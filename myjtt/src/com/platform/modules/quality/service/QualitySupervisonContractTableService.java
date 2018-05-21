package com.platform.modules.quality.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualitySupervisonContractTable;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.quality.dao.QualitySupervisonContractTableDao;

/**
 * 监理单位履约对照Service
 * @author Mr.Jia
 * @version 2017-12-10
 */
@Service
@Transactional(readOnly = true)
public class QualitySupervisonContractTableService extends CrudService<QualitySupervisonContractTableDao, QualitySupervisonContractTable> {

	@Resource
	private QualitySupervisonContractTableDao qualitySupervisonContractTableDao;
	
	public QualitySupervisonContractTable get(String id) {
		return super.get(id);
	}
	
	public List<QualitySupervisonContractTable> findList(QualitySupervisonContractTable qualitySupervisonContractTable) {
		qualitySupervisonContractTable.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualitySupervisonContractTable);
	}
	
	public Page<QualitySupervisonContractTable> findPage(Page<QualitySupervisonContractTable> page, QualitySupervisonContractTable qualitySupervisonContractTable) {
		qualitySupervisonContractTable.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualitySupervisonContractTable);
	}
	
	@Transactional(readOnly = false)
	public void save(QualitySupervisonContractTable qualitySupervisonContractTable) {
		super.save(qualitySupervisonContractTable);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualitySupervisonContractTable qualitySupervisonContractTable) {
		super.delete(qualitySupervisonContractTable);
	}
	
}