package com.platform.modules.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.common.utils.IdGen;
import com.platform.modules.project.entity.ProSimpleApprove;
import com.platform.modules.project.entity.ProSimpleBridgeTunnel;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.entity.ProSimpleSupervision;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.project.dao.ProSimpleApproveDao;
import com.platform.modules.project.dao.ProSimpleBridgeTunnelDao;
import com.platform.modules.project.dao.ProSimpleInfoDao;
import com.platform.modules.project.dao.ProSimpleSupervisionDao;

/**
 * 项目基本信息Service
 * @author Mr.Jia
 * @version 2017-12-06
 */
@Service
@Transactional(readOnly = true)
public class ProSimpleInfoService extends CrudService<ProSimpleInfoDao, ProSimpleInfo> {

	@Resource
	private ProSimpleInfoDao proSimpleInfoDao;
	@Resource
	private ProSimpleApproveDao proSimpleApproveDao;
	@Resource
	private ProSimpleBridgeTunnelDao proSimpleBridgeTunnelDao;
	@Resource
	private ProSimpleSupervisionDao proSimpleSupervisionDao;
	
	
	public ProSimpleInfo get(String id) {
		return super.get(id);
	}
	
	public List<ProSimpleInfo> findList(ProSimpleInfo pSimpleInfo) {
		pSimpleInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(pSimpleInfo);
	}
	
	public Page<ProSimpleInfo> findPage(Page<ProSimpleInfo> page, ProSimpleInfo pSimpleInfo) {
		pSimpleInfo.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, pSimpleInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ProSimpleInfo pSimpleInfo) {
		if (pSimpleInfo.getIsNewRecord()){			// 新创建的项目
			
			pSimpleInfo.preInsert();		// 设置ID， 创建时间，创建人
			proSimpleInfoDao.insert(pSimpleInfo);	// 插入项目
			
			ProSimpleApprove proSimpleApprove = pSimpleInfo.getProSimpleApprove();
			proSimpleApprove.setId(IdGen.uuid());
			proSimpleApprove.setProjectId(pSimpleInfo.getId());
			proSimpleApproveDao.insert(proSimpleApprove);
			
			ProSimpleBridgeTunnel proSimpleBridgeTunnel = pSimpleInfo.getProSimpleBridgeTunnel();
			proSimpleBridgeTunnel.setId(IdGen.uuid());
			proSimpleBridgeTunnel.setProjectId(pSimpleInfo.getId());
			proSimpleBridgeTunnelDao.insert(proSimpleBridgeTunnel);
			
			ProSimpleSupervision proSimpleSupervision = pSimpleInfo.getProSimpleSupervision();
			proSimpleSupervision.setId(IdGen.uuid());
			proSimpleSupervision.setProjectId(pSimpleInfo.getId());
			proSimpleSupervisionDao.insert(proSimpleSupervision);
			
			
		}else{		// 修改老项目
			pSimpleInfo.preUpdate();
			proSimpleInfoDao.update(pSimpleInfo);
			
			ProSimpleApprove proSimpleApprove = pSimpleInfo.getProSimpleApprove();
			proSimpleApprove.setProjectId(pSimpleInfo.getId());
			proSimpleApproveDao.updateByProjectId(proSimpleApprove);
			
			
			ProSimpleBridgeTunnel proSimpleBridgeTunnel = pSimpleInfo.getProSimpleBridgeTunnel();
			proSimpleBridgeTunnel.setProjectId(pSimpleInfo.getId());
			proSimpleBridgeTunnelDao.updateByProjectId(proSimpleBridgeTunnel);
			
			ProSimpleSupervision proSimpleSupervision = pSimpleInfo.getProSimpleSupervision();
			proSimpleSupervision.setProjectId(pSimpleInfo.getId());
			proSimpleSupervisionDao.updateByProjectId(proSimpleSupervision);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ProSimpleInfo pSimpleInfo) {
		super.delete(pSimpleInfo);
	}
	
	/**
	 * 查找所有项目名称
	 * @param pSimpleInfo
	 * @return
	 */
	public List<ProSimpleInfo> findAllProjectNames(ProSimpleInfo pSimpleInfo) {
		return proSimpleInfoDao.findAllProjectNames(pSimpleInfo);
	}
	
	
	/**
	 * 首页统计安全信息
	 * @return
	 */
	public List<ProSimpleInfo> selectSafeReportInfo() {
		return proSimpleInfoDao.selectSafeReportInfo();
	}
	
	/**
	 * 首页质量信息一览表
	 * @return
	 */
	public List<ProSimpleInfo> selectQualityReportInfo() {
		return proSimpleInfoDao.selectQualityReportInfo();
	}
}