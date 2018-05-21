package com.platform.modules.safe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.safe.entity.SafeOpertionUserinfo;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.safe.dao.SafeOpertionUserinfoDao;

/**
 * 特种、专用设备操作人员信息Service
 * @author Mr.Jia
 * @version 2017-11-28
 */
@Service
@Transactional(readOnly = true)
public class SafeOpertionUserinfoService extends CrudService<SafeOpertionUserinfoDao, SafeOpertionUserinfo> {

	public SafeOpertionUserinfo get(String id) {
		return super.get(id);
	}
	
	public List<SafeOpertionUserinfo> findList(SafeOpertionUserinfo safeOpertionUserinfo) {
		safeOpertionUserinfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(safeOpertionUserinfo);
	}
	
	public Page<SafeOpertionUserinfo> findPage(Page<SafeOpertionUserinfo> page, SafeOpertionUserinfo safeOpertionUserinfo) {
		safeOpertionUserinfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, safeOpertionUserinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeOpertionUserinfo safeOpertionUserinfo) {
		super.save(safeOpertionUserinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeOpertionUserinfo safeOpertionUserinfo) {
		super.delete(safeOpertionUserinfo);
	}
	
}