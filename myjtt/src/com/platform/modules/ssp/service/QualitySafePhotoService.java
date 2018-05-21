package com.platform.modules.ssp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.ssp.dao.QualitySafePhotoDao;
import com.platform.modules.ssp.entity.QualitySafePhoto;
import com.platform.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class QualitySafePhotoService extends  CrudService<QualitySafePhotoDao, QualitySafePhoto>{
	
	public Page<QualitySafePhoto> findPage(Page<QualitySafePhoto> page, QualitySafePhoto qualitySafePhoto) {
		qualitySafePhoto.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, qualitySafePhoto);
	}
	
	public List<QualitySafePhoto> findList(QualitySafePhoto qualitySafePhoto) {
		qualitySafePhoto.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(qualitySafePhoto);
	}
	
	public QualitySafePhoto get(String id) {
		return super.get(id);
	}
	
	@Transactional(readOnly = false)
	public void save(QualitySafePhoto qualitySafePhoto) {
		super.save(qualitySafePhoto);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualitySafePhoto qualitySafePhoto) {
		super.delete(qualitySafePhoto);
	}

}
