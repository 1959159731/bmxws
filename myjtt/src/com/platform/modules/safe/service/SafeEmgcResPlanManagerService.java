package com.platform.modules.safe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.safe.entity.SafeEmgcResPlanManager;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.safe.dao.SafeEmgcResPlanManagerDao;

/**
 * 应急救援预案管理Service
 * @author Mr.Jia
 * @version 2017-11-22
 */
@Service
@Transactional(readOnly = true)
public class SafeEmgcResPlanManagerService extends CrudService<SafeEmgcResPlanManagerDao, SafeEmgcResPlanManager> {
	
	@Resource
	private SafeEmgcResPlanManagerDao safeEmgcResPlanManagerDao;

	public SafeEmgcResPlanManager get(String id) {
		return super.get(id);
	}
	
	public List<SafeEmgcResPlanManager> findList(SafeEmgcResPlanManager safeEmgcResPlanManager) {
		safeEmgcResPlanManager.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(safeEmgcResPlanManager);
	}
	
	public Page<SafeEmgcResPlanManager> findPage(Page<SafeEmgcResPlanManager> page, SafeEmgcResPlanManager safeEmgcResPlanManager) {
		safeEmgcResPlanManager.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, safeEmgcResPlanManager);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeEmgcResPlanManager safeEmgcResPlanManager) {
		super.save(safeEmgcResPlanManager);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeEmgcResPlanManager safeEmgcResPlanManager) {
		super.delete(safeEmgcResPlanManager);
	}

	public List<String> findContractLabel(String projectId) {
		return safeEmgcResPlanManagerDao.findContractLabel(projectId);
	}
	
}