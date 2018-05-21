package com.platform.modules.ssp.entity;


import com.platform.common.persistence.DataEntity;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.safe.entity.SafeConsPersonStation;
import com.platform.modules.safe.entity.SafeProManagerPerson;


public class QualitySafe extends DataEntity<QualitySafe>{

	private static final long serialVersionUID = 1L;
	private String id;
	private String projectSimpleName;			// 项目名称简称
	private String contractSectionLabel;		// 合同段编号
	private String dangerousTitle;				// 隐患信息标题名称
	private String safeMessage;					// 隐患信息描述
	private String photo;	
	
	
	private QualitySafe qualitySafe;
	
	public QualitySafe getQualitySafe() {
		return qualitySafe;
	}

	public void setQualitySafe(QualitySafe qualitySafe) {
		this.qualitySafe = qualitySafe;
	}

	
	public QualitySafe(){
		super();
	}
	
	public QualitySafe(String id){
		super(id);
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectSimpleName() {
		return projectSimpleName;
	}

	public void setProjectSimpleName(String projectSimpleName) {
		this.projectSimpleName = projectSimpleName;
	}

	public String getContractSectionLabel() {
		return contractSectionLabel;
	}

	public void setContractSectionLabel(String contractSectionLabel) {
		this.contractSectionLabel = contractSectionLabel;
	}

	public String getDangerousTitle() {
		return dangerousTitle;
	}

	public void setDangerousTitle(String dangerousTitle) {
		this.dangerousTitle = dangerousTitle;
	}

	public String getSafeMessage() {
		return safeMessage;
	}

	public void setSafeMessage(String safeMessage) {
		this.safeMessage = safeMessage;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


	@Override
	public String toString() {
		return "QualitySafe [id=" + id + ", projectSimpleName="
				+ projectSimpleName + ", contractSectionLabel="
				+ contractSectionLabel + ", dangerousTitle=" + dangerousTitle
				+ ", safeMessage=" + safeMessage + ", photo=" + photo
				+ ", qualitySafe=" + qualitySafe + ", proSimpleInfo="
				 + "]";
	}

	
	
	
	
}
