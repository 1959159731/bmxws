package com.platform.modules.quality.entity;

import org.hibernate.validator.constraints.Length;

import com.platform.common.persistence.DataEntity;

/**
 * 试验室人员备案信息Entity
 * @author Mr.Jia
 * @version 2017-12-13
 */
public class QualityTestroomRemarkUserinfo extends DataEntity<QualityTestroomRemarkUserinfo> {
	
	private static final long serialVersionUID = 1L;
	private String remarkId;		// 关联试验室备案信息表字段id
	private String remarkUsertype;		// 人员类型（1-中心试验室 2-施工单位试验室）
	private String name;		// 备案人员姓名
	private String qualifications;		// 从业资格
	private String qualificationCertNum;		// 资格证书编号
	
	public QualityTestroomRemarkUserinfo() {
		super();
	}

	public QualityTestroomRemarkUserinfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="关联试验室备案信息表字段id长度必须介于 1 和 64 之间")
	public String getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(String remarkId) {
		this.remarkId = remarkId;
	}
	
	@Length(min=1, max=1, message="人员类型（1-中心试验室 2-施工单位试验室）长度必须介于 1 和 1 之间")
	public String getRemarkUsertype() {
		return remarkUsertype;
	}

	public void setRemarkUsertype(String remarkUsertype) {
		this.remarkUsertype = remarkUsertype;
	}
	
	@Length(min=1, max=64, message="备案人员姓名长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="从业资格长度必须介于 1 和 64 之间")
	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
	
	@Length(min=1, max=64, message="资格证书编号长度必须介于 1 和 64 之间")
	public String getQualificationCertNum() {
		return qualificationCertNum;
	}

	public void setQualificationCertNum(String qualificationCertNum) {
		this.qualificationCertNum = qualificationCertNum;
	}
	
}