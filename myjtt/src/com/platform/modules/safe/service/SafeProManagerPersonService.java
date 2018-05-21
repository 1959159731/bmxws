package com.platform.modules.safe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.safe.entity.SafeProManagerPerson;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.safe.dao.SafeProManagerPersonDao;

/**
 * 安全生产管理人员 Service
 * @author Mr.Jia
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class SafeProManagerPersonService extends CrudService<SafeProManagerPersonDao, SafeProManagerPerson> {

	@Resource
	private SafeProManagerPersonDao safeProManagerPersonDao;
	
	public SafeProManagerPerson get(String id) {
		return super.get(id);
	}
	
	public List<SafeProManagerPerson> findList(SafeProManagerPerson safeProManagerPerson) {
		safeProManagerPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(safeProManagerPerson);
	}
	
	public Page<SafeProManagerPerson> findPage(Page<SafeProManagerPerson> page, SafeProManagerPerson safeProManagerPerson) {
		safeProManagerPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, safeProManagerPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeProManagerPerson safeProManagerPerson) {
		super.save(safeProManagerPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeProManagerPerson safeProManagerPerson) {
		super.delete(safeProManagerPerson);
	}

	public List<String> findContractLabel(String projectId) {
		return safeProManagerPersonDao.findContractLabel(projectId);
	}

}