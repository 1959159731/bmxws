package com.platform.modules.report.entity;

import com.platform.common.persistence.DataEntity;

/**
 * 安全生产统计信息Entity
 * @author Mr.Jia
 * @version 2017-12-20
 */
public class SafeProductInfo extends DataEntity<SafeProductInfo> {
	
	private static final long serialVersionUID = 1L;
		
	private String project_name;		// 项目名称
	private int productUsers;			// 安全生产管理人员数
	private int specialSub;				// 特种设备数
	private int consSub;				// 专业设备数
	private int specialOperatorUsers;	// 特种作业人员（个）
	private int specialSubUsers;		// 特种设备人员数
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public int getProductUsers() {
		return productUsers;
	}
	public void setProductUsers(int productUsers) {
		this.productUsers = productUsers;
	}
	public int getSpecialSub() {
		return specialSub;
	}
	public void setSpecialSub(int specialSub) {
		this.specialSub = specialSub;
	}
	public int getConsSub() {
		return consSub;
	}
	public void setConsSub(int consSub) {
		this.consSub = consSub;
	}
	public int getSpecialOperatorUsers() {
		return specialOperatorUsers;
	}
	public void setSpecialOperatorUsers(int specialOperatorUsers) {
		this.specialOperatorUsers = specialOperatorUsers;
	}
	public int getSpecialSubUsers() {
		return specialSubUsers;
	}
	public void setSpecialSubUsers(int specialSubUsers) {
		this.specialSubUsers = specialSubUsers;
	}
	
}