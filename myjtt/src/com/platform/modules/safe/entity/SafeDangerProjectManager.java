package com.platform.modules.safe.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 危险性较大工程管理Entity
 * @author Mr.Jia
 * @version 2017-11-27
 */
public class SafeDangerProjectManager extends DataEntity<SafeDangerProjectManager> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;					// 项目名称
	private String contractSectionType;			// 合同段类型
	private String contractSectionLabel;		// 合同段编号
	private String constructionName;			// 施工单位
	private String dangerousProjectNameAndSite;	// 危险性较大的工程名称及部位
	private String accidentType;				// 容易引发事故类型
	private String specialProgramsName;			// 对应的专项施工方案名称
	private String securityOfficerName;			// 对应的专职安全员
	private Date specialProgramsApprovalTime;	// 专项施工方案监理审批日期
	private String filePath;					// 附件上传地址
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public SafeDangerProjectManager() {
		super();
	}

	public SafeDangerProjectManager(String id){
		super(id);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getContractSectionType() {
		return contractSectionType;
	}

	public void setContractSectionType(String contractSectionType) {
		this.contractSectionType = contractSectionType;
	}

	@Length(min=1, max=64, message="项目名称长度必须介于 1 和 64 之间")
	@ExcelField(title="项目名称", align=2, sort=10)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	
	@Length(min=1, max=64, message="危险性较大的工程名称长度必须介于 1 和 64 之间")
	@ExcelField(title="危险性较大的工程名称及部位", align=2, sort=40)
	public String getDangerousProjectNameAndSite() {
		return dangerousProjectNameAndSite;
	}

	public void setDangerousProjectNameAndSite(String dangerousProjectNameAndSite) {
		this.dangerousProjectNameAndSite = dangerousProjectNameAndSite;
	}
	
	@Length(min=1, max=64, message="容易引发事故类型长度必须介于 1 和 64 之间")
	@ExcelField(title="容易引发事故类型", align=2, sort=50)
	public String getAccidentType() {
		return accidentType;
	}

	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}
	
	@Length(min=1, max=64, message="对应的专项施工方案名称长度必须介于 1 和 64 之间")
	@ExcelField(title="对应的专项施工方案", align=2, sort=60)
	public String getSpecialProgramsName() {
		return specialProgramsName;
	}

	public void setSpecialProgramsName(String specialProgramsName) {
		this.specialProgramsName = specialProgramsName;
	}
	
	@Length(min=1, max=12, message="对应的专职安全员长度必须介于 1 和 12 之间")
	@ExcelField(title="对应的专职安全员", align=2, sort=70)
	public String getSecurityOfficerName() {
		return securityOfficerName;
	}

	public void setSecurityOfficerName(String securityOfficerName) {
		this.securityOfficerName = securityOfficerName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="专项施工方案监理审批日期不能为空")
	@ExcelField(title="专项施工方案监理审批日期", align=2, sort=80)
	public Date getSpecialProgramsApprovalTime() {
		return specialProgramsApprovalTime;
	}

	public void setSpecialProgramsApprovalTime(Date specialProgramsApprovalTime) {
		this.specialProgramsApprovalTime = specialProgramsApprovalTime;
	}
	
}