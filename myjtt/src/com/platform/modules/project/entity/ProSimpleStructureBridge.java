package com.platform.modules.project.entity;

import org.hibernate.validator.constraints.Length;

import com.platform.common.persistence.DataEntity;

/**
 * 主要结构物桥梁Entity
 * @author Mr.Jia
 * @version 2017-12-08
 */
public class ProSimpleStructureBridge extends DataEntity<ProSimpleStructureBridge> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目名称
	private String contractSectionType;		// 合同段编号类型
	private String contractSectionLabel;		// 合同段编号
	private String structureName;		// 结构物名称
	private String stake;		// 桩号
	private String length;		// 长度
	private String structureType;		// 结构物形式
	private String technicalCharacter;		// 工程主要技术特点、难点
	
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public ProSimpleStructureBridge() {
		super();
	}

	public ProSimpleStructureBridge(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目名称长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=1, message="合同段编号类型长度必须介于 1 和 1 之间")
	public String getContractSectionType() {
		return contractSectionType;
	}

	public void setContractSectionType(String contractSectionType) {
		this.contractSectionType = contractSectionType;
	}
	
	@Length(min=1, max=64, message="合同段编号长度必须介于 1 和 64 之间")
	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}
	
	@Length(min=1, max=64, message="结构物名称长度必须介于 1 和 64 之间")
	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}
	
	@Length(min=1, max=64, message="桩号长度必须介于 1 和 64 之间")
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
	
	@Length(min=1, max=64, message="结构物形式长度必须介于 1 和 64 之间")
	public String getStructureType() {
		return structureType;
	}

	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}
	
	@Length(min=1, max=64, message="工程主要技术特点、难点长度必须介于 1 和 64 之间")
	public String getTechnicalCharacter() {
		return technicalCharacter;
	}

	@Override
	public String toString() {
		return "ProSimpleStructureBridge [projectId=" + projectId
				+ ", contractSectionType=" + contractSectionType
				+ ", contractSectionLabel=" + contractSectionLabel
				+ ", structureName=" + structureName + ", stake=" + stake
				+ ", length=" + length + ", structureType=" + structureType
				+ ", technicalCharacter=" + technicalCharacter
				+ ", proSimpleInfo=" + proSimpleInfo + "]";
	}

	public void setTechnicalCharacter(String technicalCharacter) {
		this.technicalCharacter = technicalCharacter;
	}
	
}