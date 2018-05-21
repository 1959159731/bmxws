package com.platform.modules.quality.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 施工单位信息Entity
 * @author Mr.Jia
 * @version 2017-12-09
 */
public class QualityConstructionInfo extends DataEntity<QualityConstructionInfo> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目Id
	private String contractSectionLabel;		// 合同段编号名称
	private String constructionName;		// 施工单位名称
	private String startingStation;		// 起讫桩号
	private String endingStation;		// 结束桩号
	private Double contractPrice;		// 合同价(万元)
	private Double majorProNum;		// 主要工程数量
	private String projectManager;		// 项目经理
	private String projectChiefEngineer;		// 项目总工
	private String engineeringMinister;		// 工程部长
	private String qc;		// 质检部长
	private String qualitySupervisor;		// 质量总监
	private String safetyDirector;		// 安全总监
	private String testDirector;		// 试验室主任
	
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public QualityConstructionInfo() {
		super();
	}

	public QualityConstructionInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目Id长度必须介于 1 和 64 之间")
	@ExcelField(title="项目名称", align=2, sort=10)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=64, message="合同段编号名称长度必须介于 1 和 64 之间")
	@ExcelField(title="合同段编号", align=2, sort=20)
	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}
	
	@Length(min=1, max=64, message="施工单位名称长度必须介于 1 和 64 之间")
	@ExcelField(title="施工单位", align=2, sort=30)
	public String getConstructionName() {
		return constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}
	
	@Length(min=1, max=64, message="起讫桩号长度必须介于 1 和 64 之间")
	@ExcelField(title="起始桩号", align=2, sort=40)
	public String getStartingStation() {
		return startingStation;
	}

	public void setStartingStation(String startingStation) {
		this.startingStation = startingStation;
	}
	
	@Length(min=1, max=64, message="结束桩号长度必须介于 1 和 64 之间")
	@ExcelField(title="结束桩号", align=2, sort=50)
	public String getEndingStation() {
		return endingStation;
	}

	public void setEndingStation(String endingStation) {
		this.endingStation = endingStation;
	}
	
	@NotNull(message="合同价(万元)不能为空")
	@ExcelField(title="合同价（万元）", align=2, sort=60)
	public Double getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(Double contractPrice) {
		this.contractPrice = contractPrice;
	}
	
	@NotNull(message="主要工程数量不能为空")
	@ExcelField(title="主要工程数量", align=2, sort=70)
	public Double getMajorProNum() {
		return majorProNum;
	}

	public void setMajorProNum(Double majorProNum) {
		this.majorProNum = majorProNum;
	}
	
	@Length(min=0, max=64, message="项目经理长度必须介于 0 和 64 之间")
	@ExcelField(title="项目经理", align=2, sort=80)
	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	
	@Length(min=0, max=64, message="项目总工长度必须介于 0 和 64 之间")
	@ExcelField(title="项目总工", align=2, sort=90)
	public String getProjectChiefEngineer() {
		return projectChiefEngineer;
	}

	public void setProjectChiefEngineer(String projectChiefEngineer) {
		this.projectChiefEngineer = projectChiefEngineer;
	}
	
	@Length(min=0, max=64, message="工程部长长度必须介于 0 和 64 之间")
	@ExcelField(title="工程部长", align=2, sort=100)
	public String getEngineeringMinister() {
		return engineeringMinister;
	}

	public void setEngineeringMinister(String engineeringMinister) {
		this.engineeringMinister = engineeringMinister;
	}
	
	@Length(min=0, max=64, message="质检部长长度必须介于 0 和 64 之间")
	@ExcelField(title="质检部长", align=2, sort=110)
	public String getQc() {
		return qc;
	}

	public void setQc(String qc) {
		this.qc = qc;
	}
	
	@Length(min=0, max=64, message="质量总监长度必须介于 0 和 64 之间")
	@ExcelField(title="质量总监", align=2, sort=120)
	public String getQualitySupervisor() {
		return qualitySupervisor;
	}

	public void setQualitySupervisor(String qualitySupervisor) {
		this.qualitySupervisor = qualitySupervisor;
	}
	
	@Length(min=0, max=64, message="安全总监长度必须介于 0 和 64 之间")
	@ExcelField(title="安全总监", align=2, sort=130)
	public String getSafetyDirector() {
		return safetyDirector;
	}

	public void setSafetyDirector(String safetyDirector) {
		this.safetyDirector = safetyDirector;
	}
	
	@Length(min=0, max=64, message="试验室主任长度必须介于 0 和 64 之间")
	@ExcelField(title="试验室主任", align=2, sort=140)
	public String getTestDirector() {
		return testDirector;
	}

	public void setTestDirector(String testDirector) {
		this.testDirector = testDirector;
	}
	
}