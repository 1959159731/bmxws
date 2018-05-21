package com.platform.modules.safe.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 应急救援预案管理Entity
 * @author Mr.Jia
 * @version 2017-11-22
 */
public class SafeEmgcResPlanManager extends DataEntity<SafeEmgcResPlanManager> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;				// 项目名称
	private String contractSectionType;		// 合同段类型
	private String contractSectionLabel;	// 合同段编号
	private String constructionName;		// 施工单位
	private String planType;				// 预案类型（综合预案、专项预案、现场处置方案）字典: safe_emgc_res_plan
	private String emergencyRescueName;		// 应急救援预案名称
	private String signSituation;			// 单位负责人签批情况
	private Date implementationDate;		// 实施日期
	private String reportDepartmentRecord;	// 是否上报相关部门备案
	private Date emergencyDrillTime;		// 应急演练时间（具体时间／无）
	private String filePath;				// 附件上传地址
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public SafeEmgcResPlanManager() {
		super();
	}

	public SafeEmgcResPlanManager(String id){
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
	
	@Length(min=0, max=64, message="预案类型（综合预案、专项预案、现场处置方案）")
	@ExcelField(title="预案类型", align=2, sort=40)
	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	@Length(min=0, max=64, message="应急救援预案名称长度必须介于 0 和 64 之间")
	@ExcelField(title="应急救援预案名称", align=2, sort=50)
	public String getEmergencyRescueName() {
		return emergencyRescueName;
	}

	public void setEmergencyRescueName(String emergencyRescueName) {
		this.emergencyRescueName = emergencyRescueName;
	}
	
	@Length(min=0, max=64, message="单位负责人签批情况长度必须介于 0 和 64 之间")
	@ExcelField(title="单位负责人签批情况", align=2, sort=60)
	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="实施日期", align=2, sort=70)
	public Date getImplementationDate() {
		return implementationDate;
	}

	public void setImplementationDate(Date implementationDate) {
		this.implementationDate = implementationDate;
	}
	
	@Length(min=0, max=1, message="是否上报相关部门备案长度必须介于 0 和 1 之间")
	@ExcelField(title="是否上报相关部门备案", align=2, sort=80)
	public String getReportDepartmentRecord() {
		return reportDepartmentRecord;
	}

	public void setReportDepartmentRecord(String reportDepartmentRecord) {
		this.reportDepartmentRecord = reportDepartmentRecord;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="应急演练时间", align=2, sort=90)
	public Date getEmergencyDrillTime() {
		return emergencyDrillTime;
	}

	public void setEmergencyDrillTime(Date emergencyDrillTime) {
		this.emergencyDrillTime = emergencyDrillTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}