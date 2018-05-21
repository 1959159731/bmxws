package com.platform.modules.project.entity;

import org.hibernate.validator.constraints.Length;

import com.platform.common.persistence.DataEntity;

/**
 * 项目特殊路基工程Entity
 * @author Mr.Jia
 * @version 2017-12-08
 */
public class ProSimpleSpecialSubgrade extends DataEntity<ProSimpleSpecialSubgrade> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目名称ID
	private String contractSectionType;		// 合同段编号类型
	private String contractSectionLabel;		// 合同段编号名称
	private String specialSubgradeName;		// 特殊路基名称
	private String stake;		// 段落桩号
	private String length;		// 长度（m）
	private String processingPlan;		// 处理方案
	
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}

	public ProSimpleSpecialSubgrade() {
		super();
	}

	public ProSimpleSpecialSubgrade(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目名称ID长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectName(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=64, message="合同段编号类型长度必须介于 1 和 64 之间")
	public String getContractSectionType() {
		return contractSectionType;
	}

	public void setContractSectionType(String contractSectionType) {
		this.contractSectionType = contractSectionType;
	}
	
	@Length(min=1, max=64, message="合同段编号名称长度必须介于 1 和 64 之间")
	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}
	
	@Length(min=1, max=64, message="特殊路基名称长度必须介于 1 和 64 之间")
	public String getSpecialSubgradeName() {
		return specialSubgradeName;
	}

	public void setSpecialSubgradeName(String specialSubgradeName) {
		this.specialSubgradeName = specialSubgradeName;
	}
	
	@Length(min=1, max=64, message="段落桩号长度必须介于 1 和 64 之间")
	public String getStake() {
		return stake;
	}

	public void setStake(String stake) {
		this.stake = stake;
	}
	
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	
	@Length(min=1, max=64, message="处理方案长度必须介于 1 和 64 之间")
	public String getProcessingPlan() {
		return processingPlan;
	}

	public void setProcessingPlan(String processingPlan) {
		this.processingPlan = processingPlan;
	}
	
}