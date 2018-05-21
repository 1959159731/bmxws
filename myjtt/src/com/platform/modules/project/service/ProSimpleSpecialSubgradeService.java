package com.platform.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.persistence.Page;
import com.platform.common.service.CrudService;
import com.platform.modules.project.entity.ProSimpleSpecialSubgrade;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.project.dao.ProSimpleSpecialSubgradeDao;

/**
 * 项目特殊路基工程Service
 * @author Mr.Jia
 * @version 2017-12-08
 */
@Service
@Transactional(readOnly = true)
public class ProSimpleSpecialSubgradeService extends CrudService<ProSimpleSpecialSubgradeDao, ProSimpleSpecialSubgrade> {

	public ProSimpleSpecialSubgrade get(String id) {
		return super.get(id);
	}
	
	public List<ProSimpleSpecialSubgrade> findList(ProSimpleSpecialSubgrade proSimpleSpecialSubgrade) {
		proSimpleSpecialSubgrade.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findList(proSimpleSpecialSubgrade);
	}
	
	public Page<ProSimpleSpecialSubgrade> findPage(Page<ProSimpleSpecialSubgrade> page, ProSimpleSpecialSubgrade proSimpleSpecialSubgrade) {
		proSimpleSpecialSubgrade.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, proSimpleSpecialSubgrade);
	}
	
	@Transactional(readOnly = false)
	public void save(ProSimpleSpecialSubgrade proSimpleSpecialSubgrade) {
		super.save(proSimpleSpecialSubgrade);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProSimpleSpecialSubgrade proSimpleSpecialSubgrade) {
		super.delete(proSimpleSpecialSubgrade);
	}
	
}