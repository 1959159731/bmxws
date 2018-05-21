package com.platform.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.project.entity.ProSimpleStructureBridge;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.project.dao.ProSimpleStructureBridgeDao;

/**
 * 主要结构物桥梁Service
 * @author Mr.Jia
 * @version 2017-12-08
 */
@Service
@Transactional(readOnly = true)
public class ProSimpleStructureBridgeService extends CrudService<ProSimpleStructureBridgeDao, ProSimpleStructureBridge> {

	public ProSimpleStructureBridge get(String id) {
		return super.get(id);
	}
	
	public List<ProSimpleStructureBridge> findList(ProSimpleStructureBridge proSimpleStructureBridge) {
		proSimpleStructureBridge.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(proSimpleStructureBridge);
	}
	
	public Page<ProSimpleStructureBridge> findPage(Page<ProSimpleStructureBridge> page, ProSimpleStructureBridge proSimpleStructureBridge) {
		proSimpleStructureBridge.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, proSimpleStructureBridge);
	}
	
	@Transactional(readOnly = false)
	public void save(ProSimpleStructureBridge proSimpleStructureBridge) {
		super.save(proSimpleStructureBridge);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProSimpleStructureBridge proSimpleStructureBridge) {
		super.delete(proSimpleStructureBridge);
	}
	
}