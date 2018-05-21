package com.platform.modules.wexin.entity;

import com.platform.common.persistence.DataEntity;

/**
 * weixin Token
 * @author junfeng.jia
 * @version 2018-03-21
 */
public class WxToken extends DataEntity<WxToken> {

	private static final long serialVersionUID = 1L;
	
	
	private String wxToken;
	private long reqTime;
	
	private WxToken(){
		super.id = "1";
	}
	
	public static WxToken getInstance(){
		return new WxToken();
	}

	public String getWxToken() {
		return wxToken;
	}
	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}
	public long getReqTime() {
		return reqTime;
	}
	public void setReqTime(long reqTime) {
		this.reqTime = reqTime;
	}
	
}