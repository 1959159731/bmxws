package com.platform.modules.quality.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.platform.common.persistence.DataEntity;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 监理单位履约对照Entity
 * @author Mr.Jia
 * @version 2017-12-10
 */
public class QualitySupervisonContractTable extends DataEntity<QualitySupervisonContractTable> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目id
	private String supervisionName;		// 监理单位名称
	private Integer contractTotalNum;		// 合同总人数
	private Integer contractDepartNum;		// 合同人员持部证人数
	private Integer contractTrainingNum;		// 合同人员持培训证人数
	private Integer incomingTotalNum;		// 实际进场人员总人数
	private Integer incomingDepartNum;		// 实际进场持部证人数
	private Integer incomingTrainingNum;		// 实际进场人员持培训证人数
	private Double incomingCertificateRate;		// 实际进场人员持证率（%）
	private Integer departReplaceNum;		// 部证更换人数
	private Integer trainingReplaceNum;		// 培训证更换人数
	private String inhighIsReplaced;		// 高驻是否更换
	private Double replcaedRate;		// 更换率（%）
	
	private ProSimpleInfo proSimpleInfo;
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public QualitySupervisonContractTable() {
		super();
	}

	public QualitySupervisonContractTable(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目id长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=64, message="监理单位名称长度必须介于 1 和 64 之间")
	public String getSupervisionName() {
		return supervisionName;
	}

	public void setSupervisionName(String supervisionName) {
		this.supervisionName = supervisionName;
	}
	
	@NotNull(message="合同总人数不能为空")
	public Integer getContractTotalNum() {
		return contractTotalNum;
	}

	public void setContractTotalNum(Integer contractTotalNum) {
		this.contractTotalNum = contractTotalNum;
	}
	
	@NotNull(message="合同人员持部证人数不能为空")
	public Integer getContractDepartNum() {
		return contractDepartNum;
	}

	public void setContractDepartNum(Integer contractDepartNum) {
		this.contractDepartNum = contractDepartNum;
	}
	
	@NotNull(message="合同人员持培训证人数不能为空")
	public Integer getContractTrainingNum() {
		return contractTrainingNum;
	}

	public void setContractTrainingNum(Integer contractTrainingNum) {
		this.contractTrainingNum = contractTrainingNum;
	}
	
	@NotNull(message="实际进场人员总人数不能为空")
	public Integer getIncomingTotalNum() {
		return incomingTotalNum;
	}

	public void setIncomingTotalNum(Integer incomingTotalNum) {
		this.incomingTotalNum = incomingTotalNum;
	}
	
	@NotNull(message="实际进场持部证人数不能为空")
	public Integer getIncomingDepartNum() {
		return incomingDepartNum;
	}

	public void setIncomingDepartNum(Integer incomingDepartNum) {
		this.incomingDepartNum = incomingDepartNum;
	}
	
	@NotNull(message="实际进场人员持培训证人数不能为空")
	public Integer getIncomingTrainingNum() {
		return incomingTrainingNum;
	}

	public void setIncomingTrainingNum(Integer incomingTrainingNum) {
		this.incomingTrainingNum = incomingTrainingNum;
	}
	
	@NotNull(message="实际进场人员持证率（%）不能为空")
	public Double getIncomingCertificateRate() {
		return incomingCertificateRate;
	}

	public void setIncomingCertificateRate(Double incomingCertificateRate) {
		this.incomingCertificateRate = incomingCertificateRate;
	}
	
	@NotNull(message="部证更换人数不能为空")
	public Integer getDepartReplaceNum() {
		return departReplaceNum;
	}

	public void setDepartReplaceNum(Integer departReplaceNum) {
		this.departReplaceNum = departReplaceNum;
	}
	
	@NotNull(message="培训证更换人数不能为空")
	public Integer getTrainingReplaceNum() {
		return trainingReplaceNum;
	}

	public void setTrainingReplaceNum(Integer trainingReplaceNum) {
		this.trainingReplaceNum = trainingReplaceNum;
	}
	
	@Length(min=1, max=1, message="高驻是否更换长度必须介于 1 和 1 之间")
	public String getInhighIsReplaced() {
		return inhighIsReplaced;
	}

	public void setInhighIsReplaced(String inhighIsReplaced) {
		this.inhighIsReplaced = inhighIsReplaced;
	}
	
	@NotNull(message="更换率（%）不能为空")
	public Double getReplcaedRate() {
		return replcaedRate;
	}

	public void setReplcaedRate(Double replcaedRate) {
		this.replcaedRate = replcaedRate;
	}
	
}