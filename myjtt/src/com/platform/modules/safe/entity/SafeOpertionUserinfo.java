package com.platform.modules.safe.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;

/**
 * 特种、专用设备操作人员信息Entity
 * @author Mr.Jia
 * @version 2017-11-28
 */
public class SafeOpertionUserinfo extends DataEntity<SafeOpertionUserinfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;				// 姓名
	private String cid;					// 身份证号
	private String gender;				// 性别
	private String certificateNo;		// 执业证书编号
	private Date certificateValid;		// 证书有效期
	private String workSite;			// 作业工点或位置
	private String opertionUserType;	// 特种、专用人员标识（1-特种，2-专用）（字典：opertion_user_type）
	
	public SafeOpertionUserinfo() {
		super();
	}

	public SafeOpertionUserinfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="姓名长度必须介于 1 和 64 之间")
	@ExcelField(title="姓名", align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=18, message="身份证号长度必须介于 1 和 18 之间")
	@ExcelField(title="身份证号", align=2, sort=20)
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	@Length(min=1, max=1, message="性别长度必须介于 1 和 1 之间")
	@ExcelField(title="性别", align=2, sort=30)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Length(min=1, max=64, message="执业证书编号长度必须介于 1 和 64 之间")
	@ExcelField(title="证书编号", align=2, sort=40)
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="有效期", align=2, sort=50)
	public Date getCertificateValid() {
		return certificateValid;
	}

	public void setCertificateValid(Date certificateValid) {
		this.certificateValid = certificateValid;
	}
	
	@Length(min=1, max=64, message="作业工点或位置长度必须介于 1 和 64 之间")
	@ExcelField(title="作业工点或位置", align=2, sort=60)
	public String getWorkSite() {
		return workSite;
	}

	public void setWorkSite(String workSite) {
		this.workSite = workSite;
	}
	
	@Length(min=1, max=1, message="特种、专用人员标识（1-特种，2-专用）（字典：opertion_user_type）长度必须介于 1 和 1 之间")
	@ExcelField(title="人员类型（特种设备/专用设备）", align=2, sort=10)
	public String getOpertionUserType() {
		return opertionUserType;
	}

	public void setOpertionUserType(String opertionUserType) {
		this.opertionUserType = opertionUserType;
	}
	
}