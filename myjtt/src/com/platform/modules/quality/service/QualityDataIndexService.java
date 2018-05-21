package com.platform.modules.quality.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.quality.entity.QualityDataIndex;
import com.platform.modules.quality.dao.QualityDataIndexDao;

/**
 * 质量检测统计数据Service
 * @author Mr.Jia
 * @version 2018-02-04
 */
@Service
@Transactional(readOnly = true)
public class QualityDataIndexService extends CrudService<QualityDataIndexDao, QualityDataIndex> {

	public QualityDataIndex get(String id) {
		return super.get(id);
	}
	
	public List<QualityDataIndex> findList(QualityDataIndex qualityDataIndex) {
		return super.findList(qualityDataIndex);
	}
	
	public Page<QualityDataIndex> findPage(Page<QualityDataIndex> page, QualityDataIndex qualityDataIndex) {
		return super.findPage(page, qualityDataIndex);
	}
	
	@Transactional(readOnly = false)
	public void save(QualityDataIndex qualityDataIndex) {
		super.save(qualityDataIndex);
	}
	
	@Transactional(readOnly = false)
	public void delete(QualityDataIndex qualityDataIndex) {
		super.delete(qualityDataIndex);
	}
	
}