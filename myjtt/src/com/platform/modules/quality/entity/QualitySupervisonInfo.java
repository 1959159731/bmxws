package com.platform.modules.quality.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 监理单位信息Entity
 * @author Mr.Jia
 * @version 2017-12-09
 */
public class QualitySupervisonInfo extends DataEntity<QualitySupervisonInfo> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目id
	private String contractSectionType;		// 合同段编号类型
	private String contractSectionLabel;		// 监理合同段编号
	private String supervisionName;		// 监理单位名称
	private String haveContractSectionLabel;		// 所管辖合施工同段编号
	private String startingStation;		// 起始桩号
	private String endingStation;		// 结束桩号
	private String supervisionServiceFee;		// 监理服务费(万元)
	private String supervisionProjectCosts;		// 监理工程造价（万元）
	private Date supervisionContractStart;		// 监理合同开始时间
	private Date supervisionContractEnd;		// 监理合同结束时间
	private String principal;		// 监理项目负责人
	
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public QualitySupervisonInfo() {
		super();
	}

	public QualitySupervisonInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目id长度必须介于 1 和 64 之间")
	@ExcelField(title="项目名称", align=2, sort=10)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=64, message="合同段编号类型长度必须介于 1 和 64 之间")
	public String getContractSectionType() {
		return contractSectionType;
	}

	public void setContractSectionType(String contractSectionType) {
		this.contractSectionType = contractSectionType;
	}
	
	@Length(min=1, max=64, message="监理合同段编号长度必须介于 1 和 64 之间")
	@ExcelField(title="监理合同段编号", align=2, sort=20)
	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}
	
	@Length(min=1, max=64, message="监理单位名称长度必须介于 1 和 64 之间")
	@ExcelField(title="监理单位", align=2, sort=30)
	public String getSupervisionName() {
		return supervisionName;
	}

	public void setSupervisionName(String supervisionName) {
		this.supervisionName = supervisionName;
	}
	
	@Length(min=1, max=255, message="所管辖合施工同段编号长度必须介于 1 和 255 之间")
	@ExcelField(title="所管辖合施工同段", align=2, sort=40)
	public String getHaveContractSectionLabel() {
		return haveContractSectionLabel;
	}

	public void setHaveContractSectionLabel(String haveContractSectionLabel) {
		this.haveContractSectionLabel = haveContractSectionLabel;
	}
	
	@Length(min=1, max=64, message="起始桩号长度必须介于 1 和 64 之间")
	@ExcelField(title="起始桩号", align=2, sort=50)
	public String getStartingStation() {
		return startingStation;
	}

	public void setStartingStation(String startingStation) {
		this.startingStation = startingStation;
	}
	
	@Length(min=1, max=64, message="结束桩号长度必须介于 1 和 64 之间")
	@ExcelField(title="结束桩号", align=2, sort=60)
	public String getEndingStation() {
		return endingStation;
	}

	public void setEndingStation(String endingStation) {
		this.endingStation = endingStation;
	}
	
	@ExcelField(title="监理服务费(万元)", align=2, sort=70)
	public String getSupervisionServiceFee() {
		return supervisionServiceFee;
	}

	public void setSupervisionServiceFee(String supervisionServiceFee) {
		this.supervisionServiceFee = supervisionServiceFee;
	}
	
	@ExcelField(title="监理工程造价(万元)", align=2, sort=80)
	public String getSupervisionProjectCosts() {
		return supervisionProjectCosts;
	}

	public void setSupervisionProjectCosts(String supervisionProjectCosts) {
		this.supervisionProjectCosts = supervisionProjectCosts;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="监理合同开始时间不能为空")
	@ExcelField(title="开始时间", align=2, sort=90)
	public Date getSupervisionContractStart() {
		return supervisionContractStart;
	}

	public void setSupervisionContractStart(Date supervisionContractStart) {
		this.supervisionContractStart = supervisionContractStart;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="结束时间", align=2, sort=100)
	public Date getSupervisionContractEnd() {
		return supervisionContractEnd;
	}

	public void setSupervisionContractEnd(Date supervisionContractEnd) {
		this.supervisionContractEnd = supervisionContractEnd;
	}
	
	@Length(min=1, max=64, message="监理项目负责人长度必须介于 1 和 64 之间")
	@ExcelField(title="项目负责人", align=2, sort=110)
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
}