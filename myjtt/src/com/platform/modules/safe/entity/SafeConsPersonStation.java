package com.platform.modules.safe.entity;

import org.hibernate.validator.constraints.Length;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 施工人员驻地管理Entity
 * @author Mr.Jia
 * @version 2017-11-28
 */
public class SafeConsPersonStation extends DataEntity<SafeConsPersonStation> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;					// 项目名称
	private String contractSectionType;			// 合同段类型
	private String contractSectionLabel;		// 合同段编号
	private String constructionName;			// 施工单位
	private String residentSources;				// 驻地来源 1、自建   2、租赁
	
	private String residentType;				// 驻地类型（活动板房、商品房、居民自建房）
	private String residentSite;				// 驻地位置
	private String teamName;					// 居住的班组和工队名称
	private String liveNum;						// 居住人数
	private String fireEquipment;				// 消防器材配备情况
	
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public SafeConsPersonStation() {
		super();
	}

	public SafeConsPersonStation(String id){
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
	
	@Length(min=1, max=64, message="驻地来源（自建、租赁）长度必须介于 1 和 64 之间")
	@ExcelField(title="驻地来源", align=2, sort=40)
	public String getResidentSources() {
		return residentSources;
	}

	public void setResidentSources(String residentSources) {
		this.residentSources = residentSources;
	}
	
	@Length(min=1, max=64, message="驻地类型（活动板房、商品房、居民自建房）长度必须介于 1 和 64 之间")
	@ExcelField(title="驻地类型", align=2, sort=50)
	public String getResidentType() {
		return residentType;
	}

	public void setResidentType(String residentType) {
		this.residentType = residentType;
	}
	
	@Length(min=1, max=64, message="驻地位置长度必须介于 1 和 64 之间")
	@ExcelField(title="驻地位置", align=2, sort=60)
	public String getResidentSite() {
		return residentSite;
	}

	public void setResidentSite(String residentSite) {
		this.residentSite = residentSite;
	}
	
	@Length(min=1, max=64, message="居住的班组和工队名称长度必须介于 1 和 64 之间")
	@ExcelField(title="居住的班组和工队名称", align=2, sort=70)
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@Length(min=1, max=12, message="居住人数长度必须介于 1 和 12 之间")
	@ExcelField(title="居住人数", align=2, sort=80)
	public String getLiveNum() {
		return liveNum;
	}

	public void setLiveNum(String liveNum) {
		this.liveNum = liveNum;
	}
	
	@Length(min=1, max=64, message="消防器材配备情况长度必须介于 1 和 64 之间")
	@ExcelField(title="消防器材配备情况", align=2, sort=90)
	public String getFireEquipment() {
		return fireEquipment;
	}

	public void setFireEquipment(String fireEquipment) {
		this.fireEquipment = fireEquipment;
	}
	
}