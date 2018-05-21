package com.platform.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.project.entity.ProSimpleStructureTunnel;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.project.dao.ProSimpleStructureTunnelDao;

/**
 * 主要结构物隧道Service
 * @author Mr.Jia
 * @version 2017-12-08
 */
@Service
@Transactional(readOnly = true)
public class ProSimpleStructureTunnelService extends CrudService<ProSimpleStructureTunnelDao, ProSimpleStructureTunnel> {

	public ProSimpleStructureTunnel get(String id) {
		return super.get(id);
	}
	
	public List<ProSimpleStructureTunnel> findList(ProSimpleStructureTunnel proSimpleStructureTunnel) {
		proSimpleStructureTunnel.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(proSimpleStructureTunnel);
	}
	
	public Page<ProSimpleStructureTunnel> findPage(Page<ProSimpleStructureTunnel> page, ProSimpleStructureTunnel proSimpleStructureTunnel) {
		proSimpleStructureTunnel.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, proSimpleStructureTunnel);
	}
	
	@Transactional(readOnly = false)
	public void save(ProSimpleStructureTunnel proSimpleStructureTunnel) {
		super.save(proSimpleStructureTunnel);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProSimpleStructureTunnel proSimpleStructureTunnel) {
		super.delete(proSimpleStructureTunnel);
	}
	
}