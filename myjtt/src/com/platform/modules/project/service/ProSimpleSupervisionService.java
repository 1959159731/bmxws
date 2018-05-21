package com.platform.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.project.entity.ProSimpleSupervision;
import com.platform.modules.project.dao.ProSimpleSupervisionDao;

/**
 * 项目施工、监理单位信息Service
 * @author Mr.Jia
 * @version 2017-12-06
 */
@Service
@Transactional(readOnly = true)
public class ProSimpleSupervisionService extends CrudService<ProSimpleSupervisionDao, ProSimpleSupervision> {

	public ProSimpleSupervision get(String id) {
		return super.get(id);
	}
	
	public List<ProSimpleSupervision> findList(ProSimpleSupervision proSimpleSupervision) {
		return super.findList(proSimpleSupervision);
	}
	
	public Page<ProSimpleSupervision> findPage(Page<ProSimpleSupervision> page, ProSimpleSupervision proSimpleSupervision) {
		return super.findPage(page, proSimpleSupervision);
	}
	
	@Transactional(readOnly = false)
	public void save(ProSimpleSupervision proSimpleSupervision) {
		super.save(proSimpleSupervision);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProSimpleSupervision proSimpleSupervision) {
		super.delete(proSimpleSupervision);
	}
	
}