package com.platform.modules.safe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.safe.entity.SafeDangerProjectManager;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.safe.dao.SafeDangerProjectManagerDao;

/**
 * 危险性较大工程管理Service
 * @author Mr.Jia
 * @version 2017-11-27
 */
@Service
@Transactional(readOnly = true)
public class SafeDangerProjectManagerService extends CrudService<SafeDangerProjectManagerDao, SafeDangerProjectManager> {

	@Resource
	private SafeDangerProjectManagerDao safeDangerProjectManagerDao;
	
	public SafeDangerProjectManager get(String id) {
		return super.get(id);
	}
	
	public List<SafeDangerProjectManager> findList(SafeDangerProjectManager safeDangerProjectManager) {
		safeDangerProjectManager.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(safeDangerProjectManager);
	}
	
	public Page<SafeDangerProjectManager> findPage(Page<SafeDangerProjectManager> page, SafeDangerProjectManager safeDangerProjectManager) {
		safeDangerProjectManager.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, safeDangerProjectManager);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeDangerProjectManager safeDangerProjectManager) {
		super.save(safeDangerProjectManager);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeDangerProjectManager safeDangerProjectManager) {
		super.delete(safeDangerProjectManager);
	}
	
	public List<String> findContractLabel(String projectId) {
		return safeDangerProjectManagerDao.findContractLabel(projectId);
	}
	
}