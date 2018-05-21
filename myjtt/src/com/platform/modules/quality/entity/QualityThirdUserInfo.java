package com.platform.modules.quality.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;

/**
 * 检测单位工作内容汇总信息Entity
 * @author Mr.Jia
 * @version 2017-12-11
 */
public class QualityThirdUserInfo extends DataEntity<QualityThirdUserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String contractSectionLabel;		// 合同段编号
	private String contractContext;		// 检测内容
	private String officeEqui;		// 办公场所、仪器设备到位情况
	private String approachUser;		// 实际进场人员
	
	private String conName;
	private String conCid;
	
	private String inName;
	private String inCid;
	
	private QualityThirdCheckInfo qualityThirdCheckInfo;
	
	private List<String[]> userList;
	
	public QualityThirdCheckInfo getQualityThirdCheckInfo() {
		return qualityThirdCheckInfo;
	}

	public void setQualityThirdCheckInfo(QualityThirdCheckInfo qualityThirdCheckInfo) {
		this.qualityThirdCheckInfo = qualityThirdCheckInfo;
	}

	public QualityThirdUserInfo() {
		super();
	}

	public QualityThirdUserInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="合同段编号长度必须介于 1 和 64 之间")
	@ExcelField(title="合同段编号", align=2, sort=10)
	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}
	
	@Length(min=1, max=1000, message="检测内容长度必须介于 1 和 1000 之间")
	@ExcelField(title="检测内容", align=2, sort=20)
	public String getContractContext() {
		return contractContext;
	}

	public void setContractContext(String contractContext) {
		this.contractContext = contractContext;
	}
	
	@Length(min=1, max=1000, message="办公场所、仪器设备到位情况长度必须介于 1 和 1000 之间")
	@ExcelField(title="办公场所、仪器设备到位情况", align=2, sort=30)
	public String getOfficeEqui() {
		return officeEqui;
	}

	public void setOfficeEqui(String officeEqui) {
		this.officeEqui = officeEqui;
	}
	
	@Length(min=1, max=5000, message="实际进场人员长度必须介于 1 和 5000 之间")
	public String getApproachUser() {
		return approachUser;
	}

	public void setApproachUser(String approachUser) {
		this.approachUser = approachUser;
	}

	public List<String[]> getUserList() {
		return userList;
	}

	public void setUserList(List<String[]> userList) {
		this.userList = userList;
	}

	@ExcelField(title="合同人员姓名", align=2, sort=40)
	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	@ExcelField(title="身份证号码", align=2, sort=50)
	public String getConCid() {
		return conCid;
	}

	public void setConCid(String conCid) {
		this.conCid = conCid;
	}

	@ExcelField(title="实际进场人员姓名", align=2, sort=60)
	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

	@ExcelField(title="身份证号码", align=2, sort=70)
	public String getInCid() {
		return inCid;
	}

	public void setInCid(String inCid) {
		this.inCid = inCid;
	}
	
	
	
}