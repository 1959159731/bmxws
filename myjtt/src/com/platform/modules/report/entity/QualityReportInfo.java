package com.platform.modules.report.entity;

import com.platform.common.persistence.DataEntity;

/**
 * 质量监管信息一览Entity
 * @author Mr.Jia
 * @version 2017-12-20
 */
public class QualityReportInfo extends DataEntity<QualityReportInfo> {
	
	private static final long serialVersionUID = 1L;
		
	private int specialSubNum;
	private int buildCompanyNum;
	private int constrCompanyNum;
	private int supervisonCompanyNum;
	private int testRoomNum;
	private int constrTestRoomNum;
	private int thirdCheckNum;
	
	public int getSpecialSubNum() {
		return specialSubNum;
	}
	public void setSpecialSubNum(int specialSubNum) {
		this.specialSubNum = specialSubNum;
	}
	public int getBuildCompanyNum() {
		return buildCompanyNum;
	}
	public void setBuildCompanyNum(int buildCompanyNum) {
		this.buildCompanyNum = buildCompanyNum;
	}
	public int getConstrCompanyNum() {
		return constrCompanyNum;
	}
	public void setConstrCompanyNum(int constrCompanyNum) {
		this.constrCompanyNum = constrCompanyNum;
	}
	public int getSupervisonCompanyNum() {
		return supervisonCompanyNum;
	}
	public void setSupervisonCompanyNum(int supervisonCompanyNum) {
		this.supervisonCompanyNum = supervisonCompanyNum;
	}
	public int getTestRoomNum() {
		return testRoomNum;
	}
	public void setTestRoomNum(int testRoomNum) {
		this.testRoomNum = testRoomNum;
	}
	public int getConstrTestRoomNum() {
		return constrTestRoomNum;
	}
	public void setConstrTestRoomNum(int constrTestRoomNum) {
		this.constrTestRoomNum = constrTestRoomNum;
	}
	public int getThirdCheckNum() {
		return thirdCheckNum;
	}
	public void setThirdCheckNum(int thirdCheckNum) {
		this.thirdCheckNum = thirdCheckNum;
	}
	
	
}