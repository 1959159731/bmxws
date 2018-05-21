package com.platform.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.project.entity.ProSimpleBridgeTunnel;
import com.platform.modules.project.dao.ProSimpleBridgeTunnelDao;

/**
 * 项目桥梁、隧道信息Service
 * @author Mr.Jia
 * @version 2017-12-06
 */
@Service
@Transactional(readOnly = true)
public class ProSimpleBridgeTunnelService extends CrudService<ProSimpleBridgeTunnelDao, ProSimpleBridgeTunnel> {

	public ProSimpleBridgeTunnel get(String id) {
		return super.get(id);
	}
	
	public List<ProSimpleBridgeTunnel> findList(ProSimpleBridgeTunnel proSimpleBridgeTunnel) {
		return super.findList(proSimpleBridgeTunnel);
	}
	
	public Page<ProSimpleBridgeTunnel> findPage(Page<ProSimpleBridgeTunnel> page, ProSimpleBridgeTunnel proSimpleBridgeTunnel) {
		return super.findPage(page, proSimpleBridgeTunnel);
	}
	
	@Transactional(readOnly = false)
	public void save(ProSimpleBridgeTunnel proSimpleBridgeTunnel) {
		super.save(proSimpleBridgeTunnel);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProSimpleBridgeTunnel proSimpleBridgeTunnel) {
		super.delete(proSimpleBridgeTunnel);
	}
	
}