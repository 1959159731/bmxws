package com.platform.modules.safe.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 特种设备操作人员Entity
 * @author Me.Jia
 * @version 2017-11-26
 */
public class SafeSpecEquOperPerson extends DataEntity<SafeSpecEquOperPerson> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;				// 项目名称
	private String contractSectionType;		// 合同段类型
	private String contractSectionLabel;	// 合同段编号
	private String constructionName;		// 施工单位
	private String name;					// 姓名
	private String gender;					// 性别
	private String operateItems;			// 操作项目
	private String practiceCertnumber;		// 执业证件编号
	private Date certificateValid;			// 证件有效期
	private String certificatePath;			// 证书
	private Date approachTime;				// 进场时间
	private Date exitTime;					// 退场时间
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public SafeSpecEquOperPerson() {
		super();
	}

	public SafeSpecEquOperPerson(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目名称长度必须介于 1 和 64 之间")
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

	@Length(min=1, max=64, message="合同段编号长度必须介于 1 和 64 之间")
	@ExcelField(title="合同段编号", align=2, sort=20)
	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}
	
	@Length(min=1, max=64, message="施工单位长度必须介于 1 和 64 之间")
	@ExcelField(title="施工单位", align=2, sort=30)
	public String getConstructionName() {
		return constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}
	
	@Length(min=1, max=12, message="姓名长度必须介于 1 和 12 之间")
	@ExcelField(title="姓名", align=2, sort=40)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=1, message="性别长度必须介于 1 和 1 之间")
	@ExcelField(title="性别", align=2, sort=50)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Length(min=1, max=64, message="操作项目长度必须介于 1 和 64 之间")
	@ExcelField(title="操作项目", align=2, sort=60)
	public String getOperateItems() {
		return operateItems;
	}

	public void setOperateItems(String operateItems) {
		this.operateItems = operateItems;
	}
	
	@Length(min=1, max=64, message="执业证件编号长度必须介于 1 和 64 之间")
	@ExcelField(title="证书编号", align=2, sort=70)
	public String getPracticeCertnumber() {
		return practiceCertnumber;
	}

	public void setPracticeCertnumber(String practiceCertnumber) {
		this.practiceCertnumber = practiceCertnumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="证件有效期不能为空")
	@ExcelField(title="证书有效期", align=2, sort=80)
	public Date getCertificateValid() {
		return certificateValid;
	}

	public void setCertificateValid(Date certificateValid) {
		this.certificateValid = certificateValid;
	}
	
	@Length(min=1, max=255, message="证书长度必须介于 1 和 255 之间")
	public String getCertificatePath() {
		return certificatePath;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="进场时间不能为空")
	@ExcelField(title="进场时间", align=2, sort=90)
	public Date getApproachTime() {
		return approachTime;
	}

	public void setApproachTime(Date approachTime) {
		this.approachTime = approachTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="退场时间", align=2, sort=100)
	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}
	
}