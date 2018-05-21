package com.platform.modules.project.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.platform.common.persistence.DataEntity;

/**
 * 项目基本信息-审批信息Entity
 * @author Mr.Jia
 * @version 2017-12-06
 */
public class ProSimpleApprove extends DataEntity<ProSimpleApprove> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目ID
	private String approvalCompany;						// 项目立项审批单位
	private String approvalNum;							// 项目立项批准文号
	private Date 	approvalTime;						// 项目立项批准时间
	private String 	approvalFilepath;					// 项目立项附件
	private String 	workersApprovalCompany;				// 工可批复审批单位
	private String 	workersApprovalNum;					// 工可批复批准文号
	private Date 	workersApprovalTime;				// 工可批复批准时间
	private String 	workersApprovalFilepath;			// 工可批复附件
	private String 	designApprovalCompany;				// 初步设计审查审批单位
	private String 	designApprovalNum;					// 初步设计审查批准文号
	private Date 	designApprovalTime;					// 初步设计审查批准时间
	private String 	designApprovalFilepath;				// 初步设计审查附件
	private String 	constructionDesignApprovalUnit;		// 施工图设计批复审批单位
	private String 	constructionDesignApprovalNum;		// 施工图设计批复批准文号
	private Date 	constructionDesignApprovalTime;		// 施工图设计批复批准时间
	private String 	constructionDesignApprovalFilepath;	// 施工图设计批复附件
	
	public ProSimpleApprove() {
		super();
	}

	public ProSimpleApprove(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目ID长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=64, message="项目立项审批单位长度必须介于 1 和 64 之间")
	public String getApprovalCompany() {
		return approvalCompany;
	}

	public void setApprovalCompany(String approvalCompany) {
		this.approvalCompany = approvalCompany;
	}
	
	@Length(min=1, max=64, message="项目立项批准文号长度必须介于 1 和 64 之间")
	public String getApprovalNum() {
		return approvalNum;
	}

	public void setApprovalNum(String approvalNum) {
		this.approvalNum = approvalNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="项目立项批准时间不能为空")
	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	@Length(min=1, max=64, message="工可批复审批单位长度必须介于 1 和 64 之间")
	public String getWorkersApprovalCompany() {
		return workersApprovalCompany;
	}

	public void setWorkersApprovalCompany(String workersApprovalCompany) {
		this.workersApprovalCompany = workersApprovalCompany;
	}
	
	@Length(min=1, max=64, message="工可批复批准文号长度必须介于 1 和 64 之间")
	public String getWorkersApprovalNum() {
		return workersApprovalNum;
	}

	public void setWorkersApprovalNum(String workersApprovalNum) {
		this.workersApprovalNum = workersApprovalNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="工可批复批准时间不能为空")
	public Date getWorkersApprovalTime() {
		return workersApprovalTime;
	}

	public void setWorkersApprovalTime(Date workersApprovalTime) {
		this.workersApprovalTime = workersApprovalTime;
	}
	
	@Length(min=1, max=64, message="初步设计审查审批单位长度必须介于 1 和 64 之间")
	public String getDesignApprovalCompany() {
		return designApprovalCompany;
	}

	public void setDesignApprovalCompany(String designApprovalCompany) {
		this.designApprovalCompany = designApprovalCompany;
	}
	
	@Length(min=1, max=64, message="初步设计审查批准文号长度必须介于 1 和 64 之间")
	public String getDesignApprovalNum() {
		return designApprovalNum;
	}

	public void setDesignApprovalNum(String designApprovalNum) {
		this.designApprovalNum = designApprovalNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="初步设计审查批准时间不能为空")
	public Date getDesignApprovalTime() {
		return designApprovalTime;
	}

	public void setDesignApprovalTime(Date designApprovalTime) {
		this.designApprovalTime = designApprovalTime;
	}
	
	@Length(min=1, max=64, message="施工图设计批复审批单位长度必须介于 1 和 64 之间")
	public String getConstructionDesignApprovalUnit() {
		return constructionDesignApprovalUnit;
	}

	public void setConstructionDesignApprovalUnit(String constructionDesignApprovalUnit) {
		this.constructionDesignApprovalUnit = constructionDesignApprovalUnit;
	}
	
	@Length(min=1, max=64, message="施工图设计批复批准文号长度必须介于 1 和 64 之间")
	public String getConstructionDesignApprovalNum() {
		return constructionDesignApprovalNum;
	}

	public void setConstructionDesignApprovalNum(String constructionDesignApprovalNum) {
		this.constructionDesignApprovalNum = constructionDesignApprovalNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="施工图设计批复批准时间不能为空")
	public Date getConstructionDesignApprovalTime() {
		return constructionDesignApprovalTime;
	}

	public void setConstructionDesignApprovalTime(Date constructionDesignApprovalTime) {
		this.constructionDesignApprovalTime = constructionDesignApprovalTime;
	}

	public String getApprovalFilepath() {
		return approvalFilepath;
	}

	public void setApprovalFilepath(String approvalFilepath) {
		this.approvalFilepath = approvalFilepath;
	}

	public String getWorkersApprovalFilepath() {
		return workersApprovalFilepath;
	}

	public void setWorkersApprovalFilepath(String workersApprovalFilepath) {
		this.workersApprovalFilepath = workersApprovalFilepath;
	}

	public String getDesignApprovalFilepath() {
		return designApprovalFilepath;
	}

	public void setDesignApprovalFilepath(String designApprovalFilepath) {
		this.designApprovalFilepath = designApprovalFilepath;
	}

	public String getConstructionDesignApprovalFilepath() {
		return constructionDesignApprovalFilepath;
	}

	public void setConstructionDesignApprovalFilepath(String constructionDesignApprovalFilepath) {
		this.constructionDesignApprovalFilepath = constructionDesignApprovalFilepath;
	}
	
}