package com.platform.modules.safe.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 安全生产管理人员 Entity
 * @author Mr.Jia
 * @version 2017-11-26
 */
public class SafeProManagerPerson extends DataEntity<SafeProManagerPerson> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;					// 项目名称
	private String contractSectionType;			// 合同段类型
	private String contractSectionLabel;		// 合同段编号
	private String constructionName;			// 施工单位
	private String name;						// 姓名
	private String safetyProductionCertnumber;	// 安全生产考核合格证书编号
	private Date certificateValid;				// 证书有效期
	private String certificatePath;				// 证书保存地址
	private String jobTitle;					// 专业及职称
	private String positon;						// 职务
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public SafeProManagerPerson() {
		super();
	}

	public SafeProManagerPerson(String id){
		super(id);
	}

	@Length(min=0, max=64, message="项目名称长度必须介于 0 和 64 之间")
	@ExcelField(title="项目名称", align=2, sort=10)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getContractSectionType() {
		return contractSectionType;
	}

	public void setContractSectionType(String contractSectionType) {
		this.contractSectionType = contractSectionType;
	}

	@Length(min=0, max=64, message="合同段编号长度必须介于 0 和 64 之间")
	@ExcelField(title="合同段编号", align=2, sort=20)
	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}
	
	@Length(min=0, max=64, message="施工单位长度必须介于 0 和 64 之间")
	@ExcelField(title="施工单位", align=2, sort=30)
	public String getConstructionName() {
		return constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}
	
	@Length(min=0, max=12, message="姓名长度必须介于 0 和 12 之间")
	@ExcelField(title="姓名", align=2, sort=40)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="安全生产考核合格证书编号长度必须介于 0 和 64 之间")
	@ExcelField(title="证书编号", align=2, sort=70)
	public String getSafetyProductionCertnumber() {
		return safetyProductionCertnumber;
	}

	public void setSafetyProductionCertnumber(String safetyProductionCertnumber) {
		this.safetyProductionCertnumber = safetyProductionCertnumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="有效期", align=2, sort=80)
	public Date getCertificateValid() {
		return certificateValid;
	}

	public void setCertificateValid(Date certificateValid) {
		this.certificateValid = certificateValid;
	}
	
	@Length(min=0, max=255, message="证书保存地址长度必须介于 0 和 255 之间")
	@NotNull(message="证书不能为空，请上传证书")
	public String getCertificatePath() {
		return certificatePath;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}
	
	@Length(min=0, max=64, message="专业及职称长度必须介于 0 和 64 之间")
	@ExcelField(title="专业及职称", align=2, sort=50)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	@Length(min=0, max=64, message="职务长度必须介于 0 和 64 之间")
	@ExcelField(title="职务", align=2, sort=60)
	public String getPositon() {
		return positon;
	}

	public void setPositon(String positon) {
		this.positon = positon;
	}
	
}