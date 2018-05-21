package com.platform.modules.quality.entity;

import org.hibernate.validator.constraints.Length;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 第三方检测单位信息Entity
 * @author Mr.Jia
 * @version 2017-12-10
 */
public class QualityThirdCheckInfo extends DataEntity<QualityThirdCheckInfo> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目名称
	private String contractSectionLabel;		// 合同段编号
	private String detectionUnit;		// 检测单位
	private String indetectionContractSectionLabel;		// 所检测合同段
	private String inspectionLeader;		// 检测单位负责人
	private String inspectionLeaderPhonenum;		// 检测单位负责人（联系电话）
	private String onsiteInspectionLeader;		// 现场检查负责人
	private String onsiteInspectionLeaderPhonenum;		// 现场检查负责人（联系电话）
	
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public QualityThirdCheckInfo() {
		super();
	}

	public QualityThirdCheckInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目名称长度必须介于 1 和 64 之间")
	@ExcelField(title="项目名称", align=2, sort=10)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@ExcelField(title="合同段编号", align=2, sort=20)
	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}
	
	@Length(min=1, max=64, message="所检测合同段长度必须介于 1 和 64 之间")
	@ExcelField(title="所检测合同段", align=2, sort=30)
	public String getIndetectionContractSectionLabel() {
		return indetectionContractSectionLabel;
	}

	public void setIndetectionContractSectionLabel(String indetectionContractSectionLabel) {
		this.indetectionContractSectionLabel = indetectionContractSectionLabel;
	}
	
	@Length(min=1, max=64, message="检测单位长度必须介于 1 和 64 之间")
	@ExcelField(title="检测单位", align=2, sort=40)
	public String getDetectionUnit() {
		return detectionUnit;
	}

	public void setDetectionUnit(String detectionUnit) {
		this.detectionUnit = detectionUnit;
	}
	
	@Length(min=1, max=64, message="检测单位负责人长度必须介于 1 和 64 之间")
	@ExcelField(title="检测单位负责人", align=2, sort=50)
	public String getInspectionLeader() {
		return inspectionLeader;
	}

	public void setInspectionLeader(String inspectionLeader) {
		this.inspectionLeader = inspectionLeader;
	}
	
	@Length(min=1, max=64, message="检测单位负责人（联系电话）长度必须介于 1 和 64 之间")
	@ExcelField(title="联系电话", align=2, sort=60)
	public String getInspectionLeaderPhonenum() {
		return inspectionLeaderPhonenum;
	}

	public void setInspectionLeaderPhonenum(String inspectionLeaderPhonenum) {
		this.inspectionLeaderPhonenum = inspectionLeaderPhonenum;
	}
	
	@Length(min=1, max=64, message="现场检查负责人长度必须介于 1 和 64 之间")
	@ExcelField(title="现场检查负责人", align=2, sort=70)
	public String getOnsiteInspectionLeader() {
		return onsiteInspectionLeader;
	}

	public void setOnsiteInspectionLeader(String onsiteInspectionLeader) {
		this.onsiteInspectionLeader = onsiteInspectionLeader;
	}
	
	@Length(min=1, max=64, message="现场检查负责人（联系电话）长度必须介于 1 和 64 之间")
	@ExcelField(title="联系电话", align=2, sort=80)
	public String getOnsiteInspectionLeaderPhonenum() {
		return onsiteInspectionLeaderPhonenum;
	}

	public void setOnsiteInspectionLeaderPhonenum(String onsiteInspectionLeaderPhonenum) {
		this.onsiteInspectionLeaderPhonenum = onsiteInspectionLeaderPhonenum;
	}
	
}