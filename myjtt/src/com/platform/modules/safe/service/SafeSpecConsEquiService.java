package com.platform.modules.safe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.safe.entity.SafeSpecConsEqui;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.safe.dao.SafeSpecConsEquiDao;

/**
 * 特种设备Service
 * @author Mr.Jia
 * @version 2017-12-04
 */
@Service
@Transactional(readOnly = true)
public class SafeSpecConsEquiService extends CrudService<SafeSpecConsEquiDao, SafeSpecConsEqui> {

	@Resource
	private SafeSpecConsEquiDao safeSpecConsEquiDao;
	
	public SafeSpecConsEqui get(String id) {
		return super.get(id);
	}
	
	public List<SafeSpecConsEqui> findList(SafeSpecConsEqui safeSpecConsEqui) {
		safeSpecConsEqui.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(safeSpecConsEqui);
	}
	
	public Page<SafeSpecConsEqui> findPage(Page<SafeSpecConsEqui> page, SafeSpecConsEqui safeSpecConsEqui) {
		safeSpecConsEqui.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, safeSpecConsEqui);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeSpecConsEqui safeSpecConsEqui) {
		super.save(safeSpecConsEqui);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeSpecConsEqui safeSpecConsEqui) {
		super.delete(safeSpecConsEqui);
	}
	
	public List<String> findContractLabel(String projectId) {
		return safeSpecConsEquiDao.findContractLabel(projectId);
	}
}