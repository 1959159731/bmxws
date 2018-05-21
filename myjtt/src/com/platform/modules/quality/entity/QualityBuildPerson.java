package com.platform.modules.quality.entity;

import org.hibernate.validator.constraints.Length;

import com.platform.common.persistence.DataEntity;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 建设单位管理人员Entity
 * @author Mr.Jia
 * @version 2017-12-09
 */
public class QualityBuildPerson extends DataEntity<QualityBuildPerson> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目名称
	private String buildCompany;		// 建设单位名称
	private String name;		// 姓名
	private String department;		// 部门
	private String post;		// 岗位
	private String jobTitle;		// 职称
	private String idcard;		// 身份证号码
	private String remarks;	// 备注
	
	@Length(min=0, max=255)
	@ExcelField(title="备注", align=2, sort=1000)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public QualityBuildPerson() {
		super();
	}

	public QualityBuildPerson(String id){
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
	
	@Length(min=1, max=64, message="建设单位名称长度必须介于 1 和 64 之间")
	@ExcelField(title="建设单位", align=2, sort=20)
	public String getBuildCompany() {
		return buildCompany;
	}

	public void setBuildCompany(String buildCompany) {
		this.buildCompany = buildCompany;
	}
	
	@Length(min=1, max=64, message="姓名长度必须介于 1 和 64 之间")
	@ExcelField(title="姓名", align=2, sort=30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="部门长度必须介于 1 和 64 之间")
	@ExcelField(title="部门", align=2, sort=40)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=1, max=64, message="岗位长度必须介于 1 和 64 之间")
	@ExcelField(title="职务/岗位", align=2, sort=50)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@Length(min=1, max=64, message="职称长度必须介于 1 和 64 之间")
	@ExcelField(title="职称", align=2, sort=60)
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	@Length(min=1, max=64, message="身份证号码长度必须介于 1 和 64 之间")
	@ExcelField(title="身份证", align=2, sort=70)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
}