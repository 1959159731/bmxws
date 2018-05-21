package com.platform.modules.safe.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.safe.entity.SafeSpecEquOperPerson;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.safe.dao.SafeSpecEquOperPersonDao;

/**
 * 特种设备操作人员Service
 * @author Me.Jia
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class SafeSpecEquOperPersonService extends CrudService<SafeSpecEquOperPersonDao, SafeSpecEquOperPerson> {

	@Resource
	private SafeSpecEquOperPersonDao safeSpecEquOperPersonDao;
	
	public SafeSpecEquOperPerson get(String id) {
		return super.get(id);
	}
	
	public List<SafeSpecEquOperPerson> findList(SafeSpecEquOperPerson safeSpecEquOperPerson) {
		safeSpecEquOperPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(safeSpecEquOperPerson);
	}
	
	public Page<SafeSpecEquOperPerson> findPage(Page<SafeSpecEquOperPerson> page, SafeSpecEquOperPerson safeSpecEquOperPerson) {
		safeSpecEquOperPerson.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, safeSpecEquOperPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeSpecEquOperPerson safeSpecEquOperPerson) {
		super.save(safeSpecEquOperPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeSpecEquOperPerson safeSpecEquOperPerson) {
		super.delete(safeSpecEquOperPerson);
	}
	
	public List<String> findContractLabel(String projectId) {
		return safeSpecEquOperPersonDao.findContractLabel(projectId);
	}

}