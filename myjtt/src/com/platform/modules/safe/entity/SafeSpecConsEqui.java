package com.platform.modules.safe.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 专用设备Entity
 * @author Mr.Jia
 * @version 2017-12-04
 */
public class SafeSpecConsEqui extends DataEntity<SafeSpecConsEqui> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;							// 项目名称
	private String contractSectionType;					// 合同段编号类型
	private String contractSectionLabel;				// 合同段编号
	private String constructionName;					// 施工单位名称
	private String managementNumber;					// 管理编号
	private String specification;						// 规格型号
	private String manufacturer;						// 生产厂家
	private Date productionDate;						// 生产日期
	private String equipmentSource;						// 设备来源
	private Date approachTime;							// 进场时间
	private Date exitTime;								// 退场时间
	private String reportCompany;						// 计算书符合报告出具单位
	private Date reportTime;							// 计算书符合单位出具时间
	private String filePath;							// 证书存储地址
	private SafeOpertionUserinfo operatorUser;			// 操作人员
	private SafeOpertionUserinfo operatorUserOther;		// 包含多个
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	@Length(min=1, max=64, message="计算书符合报告出具单位长度必须介于 1 和 64 之间")
	@ExcelField(title="计算书符合报告出具单位", align=2, sort=110)
	public String getReportCompany() {
		return reportCompany;
	}

	public void setReportCompany(String reportCompany) {
		this.reportCompany = reportCompany;
	}

	@ExcelField(title="出具时间", align=2, sort=120)
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public SafeOpertionUserinfo getOperatorUser() {
		return operatorUser;
	}

	public SafeSpecConsEqui() {
		super();
	}

	public SafeSpecConsEqui(String id){
		super(id);
	}

	
	public SafeOpertionUserinfo getOperatorUserOther() {
		return operatorUserOther;
	}

	public void setOperatorUserOther(SafeOpertionUserinfo operatorUserOther) {
		this.operatorUserOther = operatorUserOther;
	}

	public void setOperatorUser(SafeOpertionUserinfo operatorUser) {
		this.operatorUser = operatorUser;
	}

	@Length(min=1, max=64, message="项目名称长度必须介于 1 和 64 之间")
	@ExcelField(title="项目名称", align=2, sort=10)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=1, max=1, message="合同段编号类型长度必须介于 1 和 1 之间")
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
	
	@Length(min=1, max=64, message="施工单位名称长度必须介于 1 和 64 之间")
	@ExcelField(title="施工单位", align=2, sort=30)
	public String getConstructionName() {
		return constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}
	
	@Length(min=1, max=64, message="管理编号长度必须介于 1 和 64 之间")
	@ExcelField(title="管理编号", align=2, sort=40)
	public String getManagementNumber() {
		return managementNumber;
	}

	public void setManagementNumber(String managementNumber) {
		this.managementNumber = managementNumber;
	}
	
	@Length(min=1, max=64, message="规格型号长度必须介于 1 和 64 之间")
	@ExcelField(title="规格型号", align=2, sort=50)
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@Length(min=1, max=64, message="生产厂家长度必须介于 1 和 64 之间")
	@ExcelField(title="生产厂家", align=2, sort=60)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="生产日期不能为空")
	@ExcelField(title="生产日期", align=2, sort=70)
	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	
	@ExcelField(title="设备来源", align=2, sort=80)
	public String getEquipmentSource() {
		return equipmentSource;
	}

	public void setEquipmentSource(String equipmentSource) {
		this.equipmentSource = equipmentSource;
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
	
	@ExcelField(title="退场时间", align=2, sort=100)
	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}
	
	@Length(min=1, max=255, message="证书存储地址长度必须介于 1 和 255 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}