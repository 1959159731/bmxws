package com.platform.modules.project.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.platform.common.persistence.DataEntity;
import com.platform.modules.report.entity.QualityReportInfo;
import com.platform.modules.report.entity.SafeProductInfo;
import com.platform.modules.sys.entity.Area;

/**
 * 项目基本信息Entity
 * @author Mr.Jia
 * @version 2017-12-06
 */
public class ProSimpleInfo extends DataEntity<ProSimpleInfo> {
	
	private static final long serialVersionUID = 1L;
	
	private String projectName;									// 项目名称
	private String projectSimpleName;							// 简称
	private String projectType;									// 项目类型
	private String projectLegal;								// 项目法人
	private String siteManagementAgencies;						// 现场管理机构
	private String designUnit;									// 设计单位
	private Double buildMileage;								// 建设里程（km）
	private Double designSpeed;									// 设计时速（km/h）
	private Double subgradeWidth;								// 路基宽度（m）
	private Double bridgeRatio;									// 桥隧比例（%）
	private Double projectEstimate;								// 工程概算（万元）
	private Double constructionResetFee;						// 建安费（万元）
	private Date appSupervisionTime;							// 办理监督手续时间
	private Date manSupervisionTime;							// 监督管理受理时间
	private Double approveDuration;								// 批准工期（月）
	private Double contractDuration;							// 合同工期（月）
	private Date proposedStartTime;								// （拟）开工时间
	private Date proposedDeliveryTime;							// 拟交工时间
	
	private ProSimpleApprove proSimpleApprove;					// 项目基本信息-审批信息
	private ProSimpleBridgeTunnel proSimpleBridgeTunnel;		// 项目桥梁、隧道信息
	private ProSimpleSupervision proSimpleSupervision;			// 项目施工、监理单位信息
	
	private Area area;		// 归属区域
	
	private String[][] exportAppData;
	private String[][] exportNormalData;
	private String[][] exportBridgeData;
	private String[][] exportSupervision;
	
	
	private SafeProductInfo safeProductInfo;		// 首页安全统计信息
	
	private QualityReportInfo qualityReportInfo;	// 首页质量管理一览信息
	
	public QualityReportInfo getQualityReportInfo() {
		return qualityReportInfo;
	}

	public void setQualityReportInfo(QualityReportInfo qualityReportInfo) {
		this.qualityReportInfo = qualityReportInfo;
	}

	public SafeProductInfo getSafeProductInfo() {
		return safeProductInfo;
	}

	public void setSafeProductInfo(SafeProductInfo safeProductInfo) {
		this.safeProductInfo = safeProductInfo;
	}

	public String[][] getExportSupervision() {
		return exportSupervision;
	}

	public void setExportSupervision(String[][] exportSupervision) {
		this.exportSupervision = exportSupervision;
	}

	public String[][] getExportBridgeData() {
		return exportBridgeData;
	}

	public void setExportBridgeData(String[][] exportBridgeData) {
		this.exportBridgeData = exportBridgeData;
	}

	public String[][] getExportNormalData() {
		return exportNormalData;
	}

	public void setExportNormalData(String[][] exportNormalData) {
		this.exportNormalData = exportNormalData;
	}

	public String[][] getExportAppData() {
		return exportAppData;
	}

	public void setExportAppData(String[][] exportAppData) {
		this.exportAppData = exportAppData;
	}

	public ProSimpleInfo() {
		super();
	}

	public ProSimpleInfo(String id){
		super(id);
	}
	
	public ProSimpleBridgeTunnel getProSimpleBridgeTunnel() {
		return proSimpleBridgeTunnel;
	}

	public void setProSimpleBridgeTunnel(ProSimpleBridgeTunnel proSimpleBridgeTunnel) {
		this.proSimpleBridgeTunnel = proSimpleBridgeTunnel;
	}

	public ProSimpleSupervision getProSimpleSupervision() {
		return proSimpleSupervision;
	}

	public void setProSimpleSupervision(ProSimpleSupervision proSimpleSupervision) {
		this.proSimpleSupervision = proSimpleSupervision;
	}

	public ProSimpleApprove getProSimpleApprove() {
		return proSimpleApprove;
	}

	public void setProSimpleApprove(ProSimpleApprove proSimpleApprove) {
		this.proSimpleApprove = proSimpleApprove;
	}

	@Length(min=1, max=64, message="项目名称长度必须介于 1 和 64 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=1, max=64, message="简称长度必须介于 1 和 64 之间")
	public String getProjectSimpleName() {
		return projectSimpleName;
	}

	public void setProjectSimpleName(String projectSimpleName) {
		this.projectSimpleName = projectSimpleName;
	}
	
	@Length(min=1, max=1, message="项目类型长度必须介于 1 和 1 之间")
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	@Length(min=1, max=64, message="项目法人长度必须介于 1 和 64 之间")
	public String getProjectLegal() {
		return projectLegal;
	}

	public void setProjectLegal(String projectLegal) {
		this.projectLegal = projectLegal;
	}
	
	@Length(min=1, max=64, message="现场管理机构长度必须介于 1 和 64 之间")
	public String getSiteManagementAgencies() {
		return siteManagementAgencies;
	}

	public void setSiteManagementAgencies(String siteManagementAgencies) {
		this.siteManagementAgencies = siteManagementAgencies;
	}
	
	@Length(min=1, max=64, message="设计单位长度必须介于 1 和 64 之间")
	public String getDesignUnit() {
		return designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}
	
	@NotNull(message="建设里程（km）不能为空")
	public Double getBuildMileage() {
		return buildMileage;
	}

	public void setBuildMileage(Double buildMileage) {
		this.buildMileage = buildMileage;
	}
	
	@NotNull(message="设计时速（km/h）不能为空")
	public Double getDesignSpeed() {
		return designSpeed;
	}

	public void setDesignSpeed(Double designSpeed) {
		this.designSpeed = designSpeed;
	}
	
	@NotNull(message="路基宽度（m）不能为空")
	public Double getSubgradeWidth() {
		return subgradeWidth;
	}

	public void setSubgradeWidth(Double subgradeWidth) {
		this.subgradeWidth = subgradeWidth;
	}
	
	@NotNull(message="桥隧比例（%）不能为空")
	public Double getBridgeRatio() {
		return bridgeRatio;
	}

	public void setBridgeRatio(Double bridgeRatio) {
		this.bridgeRatio = bridgeRatio;
	}
	
	@NotNull(message="工程概算（万元）不能为空")
	public Double getProjectEstimate() {
		return projectEstimate;
	}

	public void setProjectEstimate(Double projectEstimate) {
		this.projectEstimate = projectEstimate;
	}
	
	@NotNull(message="建安费（万元）不能为空")
	public Double getConstructionResetFee() {
		return constructionResetFee;
	}

	public void setConstructionResetFee(Double constructionResetFee) {
		this.constructionResetFee = constructionResetFee;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="办理监督手续时间不能为空")
	public Date getAppSupervisionTime() {
		return appSupervisionTime;
	}

	public void setAppSupervisionTime(Date appSupervisionTime) {
		this.appSupervisionTime = appSupervisionTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="监督管理受理时间不能为空")
	public Date getManSupervisionTime() {
		return manSupervisionTime;
	}

	public void setManSupervisionTime(Date manSupervisionTime) {
		this.manSupervisionTime = manSupervisionTime;
	}
	
	@NotNull(message="批准工期（月）不能为空")
	public Double getApproveDuration() {
		return approveDuration;
	}

	public void setApproveDuration(Double approveDuration) {
		this.approveDuration = approveDuration;
	}
	
	@NotNull(message="合同工期（月）不能为空")
	public Double getContractDuration() {
		return contractDuration;
	}

	public void setContractDuration(Double contractDuration) {
		this.contractDuration = contractDuration;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="（拟）开工时间不能为空")
	public Date getProposedStartTime() {
		return proposedStartTime;
	}

	public void setProposedStartTime(Date proposedStartTime) {
		this.proposedStartTime = proposedStartTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="拟交工时间不能为空")
	public Date getProposedDeliveryTime() {
		return proposedDeliveryTime;
	}

	public void setProposedDeliveryTime(Date proposedDeliveryTime) {
		this.proposedDeliveryTime = proposedDeliveryTime;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
}