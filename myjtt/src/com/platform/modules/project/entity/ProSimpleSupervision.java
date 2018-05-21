package com.platform.modules.project.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.platform.common.persistence.DataEntity;

/**
 * 项目施工、监理单位信息Entity
 * @author Mr.Jia
 * @version 2017-12-06
 */
public class ProSimpleSupervision extends DataEntity<ProSimpleSupervision> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;					// 项目ID
	private Integer contractSectionNum;			// 施工合同段（个）
	private String supervisionMode;				// 监理模式
	private Integer supervisionSectionNum;		// 监理合同段（个）
	private String projectContactUsername;		// 项目联系人
	private String projectContactPhone;			// 联系电话
	
	public ProSimpleSupervision() {
		super();
	}

	public ProSimpleSupervision(String id){
		super(id);
	}

	@Length(min=1, max=64, message="project_id长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@NotNull(message="contract_section_num不能为空")
	public Integer getContractSectionNum() {
		return contractSectionNum;
	}

	public void setContractSectionNum(Integer contractSectionNum) {
		this.contractSectionNum = contractSectionNum;
	}
	
	@Length(min=1, max=64, message="supervision_mode长度必须介于 1 和 64 之间")
	public String getSupervisionMode() {
		return supervisionMode;
	}

	public void setSupervisionMode(String supervisionMode) {
		this.supervisionMode = supervisionMode;
	}
	
	@NotNull(message="supervision_section_num不能为空")
	public Integer getSupervisionSectionNum() {
		return supervisionSectionNum;
	}

	public void setSupervisionSectionNum(Integer supervisionSectionNum) {
		this.supervisionSectionNum = supervisionSectionNum;
	}
	
	@Length(min=1, max=64, message="project_contact_username长度必须介于 1 和 64 之间")
	public String getProjectContactUsername() {
		return projectContactUsername;
	}

	public void setProjectContactUsername(String projectContactUsername) {
		this.projectContactUsername = projectContactUsername;
	}
	
	@Length(min=1, max=32, message="project_contact_phone长度必须介于 1 和 32 之间")
	public String getProjectContactPhone() {
		return projectContactPhone;
	}

	public void setProjectContactPhone(String projectContactPhone) {
		this.projectContactPhone = projectContactPhone;
	}
	
}