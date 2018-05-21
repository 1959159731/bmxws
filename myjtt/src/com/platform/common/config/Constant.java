package com.platform.common.config;

public class Constant {

	public static final String NONE = "无";
	
	public static final String SEX_MAN_NUM = "1";
	public static final String SEX_WOMAN_NUM = "2";
	
	public static final String SEX_MAN = "男";
	public static final String SEX_WOMAN = "女";
	
	
	public static final String getContractSectionType(String contractSectionType){
		switch (contractSectionType) {
		case "1":
			return "LJ-";
		case "2":
			return "LM-";
		case "3":
			return "JA-";
		case "4":
			return "JD-";
		case "5":
			return "FJ-";
		case "6":
			return "";
			
		case "LJ-":
			return "1";
		case "LM-":
			return "2";
		case "JA-":
			return "3";
		case "JD-":
			return "4";
		case "FJ-":
			return "5";
		default:
			return "6";
		}
	}
	
	
	public static final String getYesNo(String yesNo){
		switch (yesNo) {
		case Global.YES:
			return "是";
		case Global.NO:
			return "否";
		default:
			break;
		}
		return null;
	}
	
}
