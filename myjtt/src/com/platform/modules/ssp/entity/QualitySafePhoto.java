package com.platform.modules.ssp.entity;

import com.platform.common.persistence.DataEntity;

public class QualitySafePhoto extends DataEntity<QualitySafePhoto>{
	private String projectSimpleName;
	private String photo;
	private QualitySafe qualitySafe;
	
	public QualitySafePhoto(){
		super();
	}
	
	public QualitySafePhoto(String id){
		super(id);
	}
	
	public String getProjectSimpleName() {
		return projectSimpleName;
	}
	public void setProjectSimpleName(String projectSimpleName) {
		this.projectSimpleName = projectSimpleName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public QualitySafe getQualitySafe() {
		return qualitySafe;
	}
	public void setQualitySafe(QualitySafe qualitySafe) {
		this.qualitySafe = qualitySafe;
	}
	

}
