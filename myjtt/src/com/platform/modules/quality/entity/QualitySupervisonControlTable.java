package com.platform.modules.quality.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;

/**
 * 监理单位进场人员信息Entity
 * @author Mr.Jia
 * @version 2017-12-09
 */
public class QualitySupervisonControlTable extends DataEntity<QualitySupervisonControlTable> {
	
	private static final long serialVersionUID = 1L;
	private String supervisonName;		// 监理单位
	private String contractUsername;		// 合同人员姓名
	private String contractPost;		// 合同人员岗位
	private String contractJobTitle;		// 合同人员职称
	private String incomingUsername;		// 进场人员姓名
	private String incomingPost;		// 进场人员岗位
	private String incomingJobTitle;		// 进场人员职称
	private String incomingCid;		// 进场人员身份证号码
	private String incomingCertificateNo;		// 监理资格证书编号
	private String isRegister;		// 注册情况
	private Date incomingApproachTime;		// 进场人员进场时间
	
	public QualitySupervisonControlTable() {
		super();
	}

	public QualitySupervisonControlTable(String id){
		super(id);
	}

	@Length(min=1, max=64, message="监理单位长度必须介于 1 和 64 之间")
	@ExcelField(title="监理单位", align=2, sort=10)
	public String getSupervisonName() {
		return supervisonName;
	}

	public void setSupervisonName(String supervisonName) {
		this.supervisonName = supervisonName;
	}
	
	@ExcelField(title="合同人员姓名", align=2, sort=20)
	public String getContractUsername() {
		return contractUsername;
	}

	public void setContractUsername(String contractUsername) {
		this.contractUsername = contractUsername;
	}
	
	@ExcelField(title="合同人员岗位", align=2, sort=30)
	public String getContractPost() {
		return contractPost;
	}

	public void setContractPost(String contractPost) {
		this.contractPost = contractPost;
	}
	
	@ExcelField(title="合同人员职称", align=2, sort=40)
	public String getContractJobTitle() {
		return contractJobTitle;
	}

	public void setContractJobTitle(String contractJobTitle) {
		this.contractJobTitle = contractJobTitle;
	}
	
	@Length(min=0, max=64, message="进场人员姓名长度必须介于 1 和 64 之间")
	@ExcelField(title="进场人员姓名", align=2, sort=50)
	public String getIncomingUsername() {
		return incomingUsername;
	}

	public void setIncomingUsername(String incomingUsername) {
		this.incomingUsername = incomingUsername;
	}
	
	@Length(min=0, max=64, message="进场人员岗位长度必须介于 1和 64 之间")
	@ExcelField(title="进场人员岗位", align=2, sort=60)
	public String getIncomingPost() {
		return incomingPost;
	}

	public void setIncomingPost(String incomingPost) {
		this.incomingPost = incomingPost;
	}
	
	@Length(min=0, max=64, message="进场人员职称长度必须介于1 和 64 之间")
	@ExcelField(title="进场人员职称", align=2, sort=70)
	public String getIncomingJobTitle() {
		return incomingJobTitle;
	}

	public void setIncomingJobTitle(String incomingJobTitle) {
		this.incomingJobTitle = incomingJobTitle;
	}
	
	@Length(min=0, max=18, message="进场人员身份证号码长度必须介于 1 和 18 之间")
	@ExcelField(title="进场人员身份证号码", align=2, sort=80)
	public String getIncomingCid() {
		return incomingCid;
	}

	public void setIncomingCid(String incomingCid) {
		this.incomingCid = incomingCid;
	}
	
	@Length(min=0, max=64, message="监理资格证书编号长度必须介于 1 和 64 之间")
	@ExcelField(title="监理资格证书编号", align=2, sort=90)
	public String getIncomingCertificateNo() {
		return incomingCertificateNo;
	}

	public void setIncomingCertificateNo(String incomingCertificateNo) {
		this.incomingCertificateNo = incomingCertificateNo;
	}
	
	@Length(min=0, max=1, message="注册情况长度必须介于 1 和 1 之间")
	@ExcelField(title="注册情况(是/否)", align=2, sort=100)
	public String getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(String isRegister) {
		this.isRegister = isRegister;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="进场时间", align=2, sort=110)
	public Date getIncomingApproachTime() {
		return incomingApproachTime;
	}

	public void setIncomingApproachTime(Date incomingApproachTime) {
		this.incomingApproachTime = incomingApproachTime;
	}
	
}