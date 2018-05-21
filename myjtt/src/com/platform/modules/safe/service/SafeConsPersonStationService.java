package com.platform.modules.safe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.safe.entity.SafeConsPersonStation;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.safe.dao.SafeConsPersonStationDao;

/**
 * 施工人员驻地管理Service
 * @author Mr.Jia
 * @version 2017-11-28
 */
@Service
@Transactional(readOnly = true)
public class SafeConsPersonStationService extends CrudService<SafeConsPersonStationDao, SafeConsPersonStation> {

	@Resource
	private SafeConsPersonStationDao safeConsPersonStationDao;
	
	public SafeConsPersonStation get(String id) {
		return super.get(id);
	}
	
	public List<SafeConsPersonStation> findList(SafeConsPersonStation safeConsPersonStation) {
		safeConsPersonStation.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(safeConsPersonStation);
	}
	
	public Page<SafeConsPersonStation> findPage(Page<SafeConsPersonStation> page, SafeConsPersonStation safeConsPersonStation) {
		safeConsPersonStation.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, safeConsPersonStation);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeConsPersonStation safeConsPersonStation) {
		super.save(safeConsPersonStation);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeConsPersonStation safeConsPersonStation) {
		super.delete(safeConsPersonStation);
	}
	
	public List<String> findContractLabel(String projectId) {
		return safeConsPersonStationDao.findContractLabel(projectId);
	}

}