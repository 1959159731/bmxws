package com.platform.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.project.entity.ProSimpleApprove;
import com.platform.modules.project.dao.ProSimpleApproveDao;

/**
 * 项目基本信息-审批信息Service
 * @author Mr.Jia
 * @version 2017-12-06
 */
@Service
@Transactional(readOnly = true)
public class ProSimpleApproveService extends CrudService<ProSimpleApproveDao, ProSimpleApprove> {

	public ProSimpleApprove get(String id) {
		return super.get(id);
	}
	
	public List<ProSimpleApprove> findList(ProSimpleApprove proSimpleApprove) {
		return super.findList(proSimpleApprove);
	}
	
	public Page<ProSimpleApprove> findPage(Page<ProSimpleApprove> page, ProSimpleApprove proSimpleApprove) {
		return super.findPage(page, proSimpleApprove);
	}
	
	@Transactional(readOnly = false)
	public void save(ProSimpleApprove proSimpleApprove) {
		super.save(proSimpleApprove);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProSimpleApprove proSimpleApprove) {
		super.delete(proSimpleApprove);
	}
	
}