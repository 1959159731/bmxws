package com.platform.modules.ssp.service;

import java.util.List;




import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.ssp.dao.QualitySafeDao;
import com.platform.modules.ssp.entity.QualitySafe;
import com.platform.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class QualitySafeService extends CrudService<QualitySafeDao, QualitySafe>{
	
	
	public Page<QualitySafe> findPage(Page<QualitySafe> page, QualitySafe qualitySafe) {
		qualitySafe.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualitySafe);
	}
	
	public List<QualitySafe> findList(QualitySafe qualitySafe) {
		qualitySafe.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualitySafe);
	}
	
	
	
	public QualitySafe get(String id) {
		return super.get(id);
	}
	
	
	
	
	@Transactional(readOnly = false)
	public void save(QualitySafe qualitySafe) {
		super.save(qualitySafe);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualitySafe qualitySafe) {
		super.delete(qualitySafe);
	}
	
}
