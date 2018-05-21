package com.platform.modules.project.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.platform.common.persistence.DataEntity;

/**
 * 项目桥梁、隧道信息Entity
 * @author Mr.Jia
 * @version 2017-12-06
 */
public class ProSimpleBridgeTunnel extends DataEntity<ProSimpleBridgeTunnel> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// project_id
	
	private Integer extraLargeBridgeNum;		// 特大桥数量（座）
	private Double extraLargeBridgeLength;		// 累计长度（m）
	private Integer largeBridgeNum;		// 大桥（座）
	private Double largeBridgeLength;		// 累计长度（m）
	private Integer mediumBridgeNum;		// 中桥（座）
	private Double mediumBridgeLength;		// 累计长度（m）
	private Double bridgeTotalLength;		// 桥梁累计长度（m）
	
	private Integer extraLongTunnelNum;		// 特长隧道（座）
	private Double extraLongTunnelLength;		// 累计长度（m）
	private Integer longTunnelNum;		// 长隧道（座）
	private Double longTunnelLength;		// 累计长度（m）
	private Integer mediumTunnelNum;		// 中隧道（座）
	private Double mediumTunnelLength;		// 累计长度（m）
	private Integer shortTunnelNum;		// 短隧道（座）
	private Double shortTunnelLength;		// 累计长度（m）
	private Double tunnelTotalLength;		// 隧道累计长度（m）
	
	public ProSimpleBridgeTunnel() {
		super();
	}

	public ProSimpleBridgeTunnel(String id){
		super(id);
	}

	@Length(min=1, max=64, message="project_id长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@NotNull(message="特大桥数量（座）不能为空")
	public Integer getExtraLargeBridgeNum() {
		return extraLargeBridgeNum;
	}

	public void setExtraLargeBridgeNum(Integer extraLargeBridgeNum) {
		this.extraLargeBridgeNum = extraLargeBridgeNum;
	}
	
	@NotNull(message="累计长度（m）不能为空")
	public Double getExtraLargeBridgeLength() {
		return extraLargeBridgeLength;
	}

	public void setExtraLargeBridgeLength(Double extraLargeBridgeLength) {
		this.extraLargeBridgeLength = extraLargeBridgeLength;
	}
	
	@NotNull(message="大桥（座）不能为空")
	public Integer getLargeBridgeNum() {
		return largeBridgeNum;
	}

	public void setLargeBridgeNum(Integer largeBridgeNum) {
		this.largeBridgeNum = largeBridgeNum;
	}
	
	@NotNull(message="累计长度（m）不能为空")
	public Double getLargeBridgeLength() {
		return largeBridgeLength;
	}

	public void setLargeBridgeLength(Double largeBridgeLength) {
		this.largeBridgeLength = largeBridgeLength;
	}
	
	@NotNull(message="中桥（座）不能为空")
	public Integer getMediumBridgeNum() {
		return mediumBridgeNum;
	}

	public void setMediumBridgeNum(Integer mediumBridgeNum) {
		this.mediumBridgeNum = mediumBridgeNum;
	}
	
	@NotNull(message="累计长度（m）不能为空")
	public Double getMediumBridgeLength() {
		return mediumBridgeLength;
	}

	public void setMediumBridgeLength(Double mediumBridgeLength) {
		this.mediumBridgeLength = mediumBridgeLength;
	}
	
	@NotNull(message="特长隧道（座）不能为空")
	public Integer getExtraLongTunnelNum() {
		return extraLongTunnelNum;
	}

	public void setExtraLongTunnelNum(Integer extraLongTunnelNum) {
		this.extraLongTunnelNum = extraLongTunnelNum;
	}
	
	@NotNull(message="累计长度（m）不能为空")
	public Double getExtraLongTunnelLength() {
		return extraLongTunnelLength;
	}

	public void setExtraLongTunnelLength(Double extraLongTunnelLength) {
		this.extraLongTunnelLength = extraLongTunnelLength;
	}
	
	@NotNull(message="长隧道（座）不能为空")
	public Integer getLongTunnelNum() {
		return longTunnelNum;
	}

	public void setLongTunnelNum(Integer longTunnelNum) {
		this.longTunnelNum = longTunnelNum;
	}
	
	@NotNull(message="累计长度（m）不能为空")
	public Double getLongTunnelLength() {
		return longTunnelLength;
	}

	public void setLongTunnelLength(Double longTunnelLength) {
		this.longTunnelLength = longTunnelLength;
	}
	
	@NotNull(message="中隧道（座）不能为空")
	public Integer getMediumTunnelNum() {
		return mediumTunnelNum;
	}

	public void setMediumTunnelNum(Integer mediumTunnelNum) {
		this.mediumTunnelNum = mediumTunnelNum;
	}
	
	@NotNull(message="累计长度（m）不能为空")
	public Double getMediumTunnelLength() {
		return mediumTunnelLength;
	}

	public void setMediumTunnelLength(Double mediumTunnelLength) {
		this.mediumTunnelLength = mediumTunnelLength;
	}
	
	@NotNull(message="短隧道（座）不能为空")
	public Integer getShortTunnelNum() {
		return shortTunnelNum;
	}

	public void setShortTunnelNum(Integer shortTunnelNum) {
		this.shortTunnelNum = shortTunnelNum;
	}
	
	@NotNull(message="累计长度（m）不能为空")
	public Double getShortTunnelLength() {
		return shortTunnelLength;
	}

	public void setShortTunnelLength(Double shortTunnelLength) {
		this.shortTunnelLength = shortTunnelLength;
	}

	public Double getBridgeTotalLength() {
		return bridgeTotalLength;
	}

	public void setBridgeTotalLength(Double bridgeTotalLength) {
		this.bridgeTotalLength = bridgeTotalLength;
	}

	public Double getTunnelTotalLength() {
		return tunnelTotalLength;
	}

	public void setTunnelTotalLength(Double tunnelTotalLength) {
		this.tunnelTotalLength = tunnelTotalLength;
	}
	
}