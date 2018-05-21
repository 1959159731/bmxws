package com.platform.modules.safe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.safe.entity.SafeSpecEqui;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.safe.dao.SafeSpecEquiDao;

/**
 * 特种设备Service
 * @author Mr.Jia
 * @version 2017-12-04
 */
@Service
@Transactional(readOnly = true)
public class SafeSpecEquiService extends CrudService<SafeSpecEquiDao, SafeSpecEqui> {

	@Resource
	private SafeSpecEquiDao safeSpecEquiDao;
	
	public SafeSpecEqui get(String id) {
		return super.get(id);
	}
	
	public List<SafeSpecEqui> findList(SafeSpecEqui safeSpecEqui) {
		safeSpecEqui.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(safeSpecEqui);
	}
	
	public Page<SafeSpecEqui> findPage(Page<SafeSpecEqui> page, SafeSpecEqui safeSpecEqui) {
		safeSpecEqui.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, safeSpecEqui);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeSpecEqui safeSpecEqui) {
		super.save(safeSpecEqui);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeSpecEqui safeSpecEqui) {
		super.delete(safeSpecEqui);
	}
	
	public List<String> findContractLabel(String projectId) {
		return safeSpecEquiDao.findContractLabel(projectId);
	}
}