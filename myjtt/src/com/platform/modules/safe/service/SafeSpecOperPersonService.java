package com.platform.modules.safe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.safe.entity.SafeSpecOperPerson;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.safe.dao.SafeSpecOperPersonDao;

/**
 * 特种作业人员台帐Service
 * @author Mr.Jia
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class SafeSpecOperPersonService extends CrudService<SafeSpecOperPersonDao, SafeSpecOperPerson> {

	@Resource
	private SafeSpecOperPersonDao safeSpecOperPersonDao;
	
	public SafeSpecOperPerson get(String id) {
		return super.get(id);
	}
	
	public List<SafeSpecOperPerson> findList(SafeSpecOperPerson safeSpecOperPerson) {
		safeSpecOperPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(safeSpecOperPerson);
	}
	
	public Page<SafeSpecOperPerson> findPage(Page<SafeSpecOperPerson> page, SafeSpecOperPerson safeSpecOperPerson) {
		safeSpecOperPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, safeSpecOperPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeSpecOperPerson safeSpecOperPerson) {
		super.save(safeSpecOperPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeSpecOperPerson safeSpecOperPerson) {
		super.delete(safeSpecOperPerson);
	}

	public List<String> findContractLabel(String projectId) {
		return safeSpecOperPersonDao.findContractLabel(projectId);
	}
	
}