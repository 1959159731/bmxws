package com.platform.modules.quality.entity;

import org.hibernate.validator.constraints.Length;

import com.platform.common.persistence.DataEntity;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 施工单位试验室备案信息Entity
 * @author Mr.Jia
 * @version 2017-12-13
 */
public class QualityConsTestroomRemark extends DataEntity<QualityConsTestroomRemark> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目id
	private String contractSectionLabel;		// 合同段
	private String maternalLaboratory;		// 母体试验室
	private String qacertificateNum;		// 资格证书编号
	private String authorizedPerson;		// 授权负责人
	private String remarkUsers;		// 备案人员
	private String detectionProjectParameter;		// 授权开展的试验检测项目及参数
	
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public QualityConsTestroomRemark() {
		super();
	}

	public QualityConsTestroomRemark(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目id长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=64, message="合同段长度必须介于 1 和 64 之间")
	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}
	
	@Length(min=1, max=64, message="母体试验室长度必须介于 1 和 64 之间")
	public String getMaternalLaboratory() {
		return maternalLaboratory;
	}

	public void setMaternalLaboratory(String maternalLaboratory) {
		this.maternalLaboratory = maternalLaboratory;
	}
	
	@Length(min=1, max=64, message="资格证书编号长度必须介于 1 和 64 之间")
	public String getQacertificateNum() {
		return qacertificateNum;
	}

	public void setQacertificateNum(String qacertificateNum) {
		this.qacertificateNum = qacertificateNum;
	}
	
	@Length(min=1, max=64, message="授权负责人长度必须介于 1 和 64 之间")
	public String getAuthorizedPerson() {
		return authorizedPerson;
	}

	public void setAuthorizedPerson(String authorizedPerson) {
		this.authorizedPerson = authorizedPerson;
	}
	
	public String getRemarkUsers() {
		return remarkUsers;
	}

	public void setRemarkUsers(String remarkUsers) {
		this.remarkUsers = remarkUsers;
	}
	
	public String getDetectionProjectParameter() {
		return detectionProjectParameter;
	}

	public void setDetectionProjectParameter(String detectionProjectParameter) {
		this.detectionProjectParameter = detectionProjectParameter;
	}
	
}