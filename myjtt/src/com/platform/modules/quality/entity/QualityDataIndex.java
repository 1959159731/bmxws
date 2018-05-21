package com.platform.modules.quality.entity;

import java.util.Date;

import com.platform.common.persistence.DataEntity;
import com.platform.modules.project.entity.ProSimpleInfo;

/**
 * 质量检测统计数据Entity
 * @author Mr.Jia
 * @version 2018-02-04
 */
public class QualityDataIndex extends DataEntity<QualityDataIndex> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目id
	private ProSimpleInfo proSimpleInfo;
	private String projectLabel;	// 项目标段
	private String checkDate;			// 检查时间
	private String checkDateSeason; // 检查具体时间（第一、二、三、四季度，上下半年）
	
	private Date checkDateForYear;
	
	public Date getCheckDateForYear() {
		return checkDateForYear;
	}

	public void setCheckDateForYear(Date checkDateForYear) {
		this.checkDateForYear = checkDateForYear;
	}

	public String getCheckDateSeason() {
		return checkDateSeason;
	}

	public void setCheckDateSeason(String checkDateSeason) {
		this.checkDateSeason = checkDateSeason;
	}

	public String getProjectLabel() {
		return projectLabel;
	}

	public void setProjectLabel(String projectLabel) {
		this.projectLabel = projectLabel;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	private Integer earthworkCompaction;						// 土石方压实度检测点*
	private Integer earthworkCompactionPassed;					// 土石方压实度合格点
	private Integer earthworkDeflection;						// 土石方弯沉监测点*
	private Integer earthworkDeflectionPassed;					// 土石方弯沉合格点
	private Integer earthworkAshDosage;							// 土石方灰剂量监测点
	private Integer earthworkAshDosagePassed;				// 土石方灰剂量合格点
	private Integer drainageSectionSize;					// 排水断面尺寸监测点
	private Integer drainageSectionSizePassed;					// 排水断面尺寸合格点
	private Integer drainagePavedThickness;						// 排水铺砌厚度监测点
	private Integer drainagePavedThicknessPassed;			// 排水铺砌厚度合格点
	private Integer bridgeConcreteStrength;					// 小桥涵砼强度检测点*
	private Integer bridgeConcreteStrengthPassed;			// 小桥涵砼强度合格点
	private Integer bridgeReinforcedThickness;				// 钢筋保护层厚度
	private Integer bridgeReinforcedThicknessPassed;		// 钢筋保护层厚度合格点
	private Integer bridgeStructureSize;					// 小桥涵结构尺寸检测点
	private Integer bridgeStructureSizePassed;				// 小桥涵结构尺寸合格点
	private Integer bridgeCulvertSpacing;					// 小桥涵钢筋间距检测点
	private Integer bridgeCulvertSpacingPassed;				// 小桥涵钢筋间距合格点
	private Integer bridgeCulvertElevationPosition;			// 小桥涵高程及平面位置检测点
	private Integer bridgeCulvertElevationPositionPassed;		// 小桥涵高程及平面位置合格点
	private Integer supportConcreteStrength;				// 支挡工程砼强度检测点*
	private Integer supportConcreteStrengthPassed;			// 合格点
	private Integer supportSectionSize;						// 支挡工程断面尺寸检测点*
	private Integer supportSectionSizePassed;				// 支挡工程断面尺寸合格点
	private Integer subgradeEngineeringOthers;				// 其他检测点
	private Integer subgradeEngineeringOthersPassed;		// 其他合格点
	
	
	private Integer surfaceCompaction;		// 面层压实度检测点*
	private Integer surfaceCompactionPassed;		// 面层压实度合格点
	private Integer surfaceConcreteRoadStrength;		// 面层混凝土路面强度检测点*
	private Integer surfaceConcreteRoadStrengthPassed;		// 面层混凝土路面强度合格点
	private Integer surfaceDeflection;		// 面层弯沉检测点*
	private Integer surfaceDeflectionPassed;		// 面层弯沉合格点
	private Integer surfaceAsphaltContent;		// 面层沥青含量检测点
	private Integer surfaceAsphaltContentPassed;		// 面层沥青含量合格点
	private Integer surfacePorosity;		// 面层空隙率检测点
	private Integer surfacePorosityPassed;		// 面层空隙率合格点
	private Integer surfaceThickness;		// 面层厚度检测点*
	private Integer surfaceThicknessPassed;		// 面层厚度合格点
	private Integer surfaceFlatness;		// 面层平整度检测点*
	private Integer surfaceFlatnessPassed;		// 面层平整度合格点
	private Integer surfaceCrossSlope;		// 面层横坡检测点
	private Integer surfaceCrossSlopePassed;		// 面层横坡合格点
	private Integer surfaceElevation;		// 面层高程检测点
	private Integer surfaceElevationPassed;		// 面层高程合格点
	private Integer surfaceWaterSeepageCoefficient;		// 面层渗水系数检测点
	private Integer surfaceWaterSeepageCoefficientPassed;		// 面层渗水系数合格点
	private Integer surfaceSlabHeightDifference;		// 面层混凝土路面相邻板高差检测点
	private Integer surfaceSlabHeightDifferencePassed;		// 面层混凝土路面相邻板高差合格点
	private Integer surfaceAntiSlipSurface;		// 面层路面抗滑检测点
	private Integer surfaceAntiSlipSurfacePassed;		// 面层路面抗滑合格点
	private Integer grassrootsLevelCompaction;		// 基层底基层压实度检测点*
	private Integer grassrootsLevelCompactionPassed;		// 基层底基层压实度合格点
	private Integer grassrootsSubbaseGrayAmount;		// 基层底基层灰剂量检测点
	private Integer grassrootsSubbaseGrayAmountPassed;		// 基层底基层灰剂量合格点
	private Integer grassrootsThickness;		// 基层底基层厚度检测点
	private Integer grassrootsThicknessPassed;		// 基层底基层厚度合格点
	private Integer grassrootsUnitIntegrity;		// 基层底基层整体性检测点*
	private Integer grassrootsUnitIntegrityPassed;		// 基层底基层整体性合格点
	private Integer grassrootsIntensity;		// 基层底基层强度检测点*
	private Integer grassrootsIntensityPassed;		// 基层底基层强度合格点
	private Integer grassrootsLevelCracks;		// 基层底基层基层裂缝检测点
	private Integer grassrootsLevelCracksPassed;		// 基层底基层基层裂缝合格点
	private Integer grassrootsCrossSlope;		// 基地底基层横坡检测点
	private Integer grassrootsCrossSlopePassed;		// 基地底基层横坡合格点
	private Integer grassrootsElevation;		// 基地底基层高程检测点
	private Integer grassrootsElevationPassed;		// 基地底基层高程合格点
	private Integer pavementEngineeringOthers;		// 其他检测点
	private Integer pavementEngineeringOthersPassed;		// 其他合格点
	
	
	private Integer lowerstructureConcreteStrength;		// 下部结构墩台砼强度检测点*
	private Integer lowerstructureConcreteStrengthPassed;		// 下部结构墩台砼强度合格点
	private Integer lowerstructureReinforcedProtectiveThickness;		// 下部结构钢筋保护层厚度检测点
	private Integer lowerstructureReinforcedProtectiveThicknessPassed;		// 下部结构钢筋保护层厚度合格点
	private Integer lowerstructurePierVerticality;		// 下部结构墩台垂直度检测点
	private Integer lowerstructurePierVerticalityPassed;		// 下部结构墩台垂直度合格点
	private Integer lowerstructureStructureSize;		// 下部结构结构尺寸检测点
	private Integer lowerstructureStructureSizePassed;		// 下部结构结构尺寸合格点
	private Integer lowerstructureReinforcedSpacing;		// 下部结构钢筋间距检测点
	private Integer lowerstructureReinforcedSpacingPassed;		// 下部结构钢筋间距合格点
	private Integer lowerstructureElevationPlaneLocation;		// 下部结构高程及平面位置检测点
	private Integer lowerstructureElevationPlaneLocationPassed;		// 下部结构高程及平面位置合格点
	private Integer superstructureConcreteStrength;		// 上部结构砼强度检测点*
	private Integer superstructureConcreteStrengthPassed;		// 上部结构砼强度合格点
	private Integer superstructureReinforcedProtectiveThickness;		// 上部结构钢筋保护层厚度检测点
	private Integer superstructureReinforcedProtectiveThicknessPassed;		// 上部结构钢筋保护层厚度合格点
	private Integer superstructureStructureSize;		// 上部结构结构尺寸检测点
	private Integer superstructureStructureSizePassed;		// 上部结构结构尺寸合格点
	private Integer superstructureReinforcedSpacing;		// 上部结构钢筋间距检测点
	private Integer superstructureReinforcedSpacingPassed;		// 上部结构钢筋间距合格点
	private Integer superstructureElevationPlaneLocation;		// 上部结构高程及平面位置检测点
	private Integer superstructureElevationPlaneLocationPassed;		// 上部结构高程及平面位置合格点
	private Integer superstructurePrestressedHoleCoordinates;		// 上部结构预应力孔道坐标检测点
	private Integer superstructurePrestressedHoleCoordinatesPassed;		// 上部结构预应力孔道坐标合格点
	private Integer bridgeWidth;		// 桥面宽度检测点*
	private Integer bridgeWidthPassed;		// 桥面宽度合格点
	private Integer bridgeThickness;		// 桥面厚度检测点*
	private Integer bridgeThicknessPassed;		// 桥面厚度合格点
	private Integer bridgeSlope;		// 桥面横坡检测点*
	private Integer bridgeSlopePassed;		// 桥面横坡合格点
	private Integer hardenedConcreteChlorideIonContent;		// 硬化混凝土氯离子含量检测点
	private Integer hardenedConcreteChlorideIonContentPassed;		// 硬化混凝土氯离子含量合格点
	private Integer hardenedConcreteAlkaliContent;		// 硬化混凝土碱含量检测点
	private Integer hardenedConcreteAlkaliContentPassed;		// 硬化混凝土碱含量合格点
	private Integer concreteAdditives;		// 混凝土添加剂检测点
	private Integer concreteAdditivesPassed;		// 混凝土添加剂合格点
	private Integer anchorsPrestressing;		// 锚具及张拉预应力检测点
	private Integer anchorsPrestressingPassed;		// 锚具及张拉预应力合格点
	private Integer plateRubberBearingInstallationQuality;		// 板式橡胶支座及安装质量检测点
	private Integer plateRubberBearingInstallationQualityPassed;		// 板式橡胶支座及安装质量合格点
	private Integer deckTopSurfaceElevation;		// 桥面系顶面标高检测点
	private Integer deckTopSurfaceElevationPassed;		// 桥面系顶面标高合格点
	private Integer crackWidth;		// 裂缝宽度检测点
	private Integer crackWidthPassed;		// 裂缝宽度合格点
	private Integer pileFoundation;		// 桩基检测点
	private Integer pileFoundationPassed;		// 桩基合格点
	private Integer bridgeEngineeringOthers;		// 桥梁工程其他检测点
	private Integer bridgeEngineeringOthersPassed;		// 桥梁工程其他合格点
	
	
	private Integer liningSupportConcreteStrength;		// 衬砌支护砼强度检测点*
	private Integer liningSupportConcreteStrengthPassed;		// 衬砌支护砼强度合格点
	private Integer liningSupportLiningThickness;		// 衬砌支护衬砌厚度检测点*
	private Integer liningSupportLiningThicknessPassed;		// 衬砌支护衬砌厚度合格点
	private Integer liningSupportFlatness;		// 衬砌支护平整度检测点
	private Integer liningSupportFlatnessPassed;		// 衬砌支护平整度合格点
	private Integer liningSupportAnchorDrawing;		// 衬砌支护锚杆拉拔检测点
	private Integer liningSupportAnchorDrawingPassed;		// 衬砌支护锚杆拉拔合格点
	private Integer anchorSpacingLengthGrouting;		// 锚杆间距、长度及注浆实度检测点*
	private Integer anchorSpacingLengthGroutingPassed;		// 锚杆间距、长度及注浆实度合锚杆间距、长度及注浆实度格点
	private Integer clearance;		// 净空检测点*
	private Integer clearancePassed;		// 净空合格点
	private Integer sectionOutline;		// 断面轮廓检测点
	private Integer sectionOutlinePassed;		// 断面轮廓合格点
	private Integer archesSpacing;		// 拱架间距检测点
	private Integer archesSpacingPassed;		// 拱架间距合格点
	private Integer reinforcedSpacing;		// 钢筋间距检测点
	private Integer reinforcedSpacingPassed;		// 钢筋间距合格点
	private Integer empties;		// 空洞检测点
	private Integer emptiesPassed;		// 空洞合格点
	private Integer waterproofBoardWeldingWide;		// 防水板质量焊接宽度检测点
	private Integer waterproofBoardWeldingWidePassed;		// 防水板质量焊接宽度合格点
	private Integer leadSmallNumIndirect;		// 超前小导管数量或间接检测点
	private Integer leadSmallNumIndirectPassed;		// 超前小导管数量或间接合格点
	private Integer tunnelPavement;		// 隧道路面检测点
	private Integer tunnelPavementPassed;		// 隧道路面合格点
	private Integer tunnelEngineeringOthers;		// 隧道其他检测点
	private Integer tunnelEngineeringOthersPassed;		// 隧道其他合格点
	
	
	private Integer reinforced;		// 钢材检测点
	private Integer reinforcedPassed;		// 钢材合格点
	private Integer cement;		// 水泥检测点
	private Integer cementPassed;		// 水泥合格点
	private Integer asphalt;		// 沥青检测点
	private Integer asphaltPassed;		// 沥青合格点
	private Integer lime;		// 石灰检测点
	private Integer limePassed;		// 石灰合格点
	private Integer gravel;		// 碎石检测点
	private Integer gravelsandPassed;		// 碎石合格点
	private Integer sand;		// 砂检测点
	private Integer sandPassed;		// 砂合格点
	private Integer mineralPowder;		// 矿粉检测点
	private Integer mineralPowderPassed;		// 矿粉合格点
	private Integer rubberBearings;		// 橡胶支座检测点
	private Integer rubberBearingsPassed;		// 橡胶支座合格点
	private Integer anchor;		// 锚具检测点
	private Integer anchorPassed;		// 锚具合格点
	private Integer splicingBolts;		// 拼接螺栓检测点
	private Integer splicingBoltsPassed;		// 拼接螺栓合格点
	private Integer geotextile;		// 土工布检测点
	private Integer geotextilePassed;		// 土工布合格点
	private Integer waterproofBoard;		// 防水板检测点
	private Integer waterproofBoardPassed;		// 防水板合格点
	private Integer drain;		// 排水管检测点
	private Integer drainPassed;		// 排水管合格点
	private Integer slurry;		// 压浆料检测点
	private Integer slurryPassed;		// 压浆料合格点
	private Integer materialsOthers;		// 原材料其他检测点
	private Integer materialsOthersPassed;		// 原材料其他合格点
	
	
	private Integer componentBaseThickness;		// 构件基底厚度检测点
	private Integer componentBaseThicknessPassed;		// 构件基底厚度合格点
	private Integer componentCoatingThickness;		// 构件防腐层厚度检测点
	private Integer componentCoatingThicknessPassed;		// 构件防腐层厚度合格点
	private Integer fencebeamCenterHeight;		// 护栏横梁中心高度检测点*
	private Integer fencebeamCenterHeightPassed;		// 护栏横梁中心高度合格点
	private Integer fenceColumnEmbeddedDepth;		// 护栏立柱埋入深度检测点
	private Integer fenceColumnEmbeddedDepthPassed;		// 护栏立柱埋入深度合格点
	private Integer stitchingBoltTensileStrength;		// 拼接螺栓抗拉强度检测点
	private Integer stitchingBoltTensileStrengthPassed;		// 拼接螺栓抗拉强度合格点
	private Integer pillarWallThickness;		// 立柱壁厚度检测点*
	private Integer pillarWallThicknessPassed;		// 立柱壁厚度合格点
	private Integer concreteFenceStrength;		// 混凝土护栏强度检测点*
	private Integer concreteFenceStrengthPassed;		// 混凝土护栏强度合格点
	private Integer wavePlateThickness;		// 波形板厚度检测点*
	private Integer wavePlateThicknessPassed;		// 波形板厚度合格点
	private Integer signBoardClearance;		// 标志板垂直度与净空检测点
	private Integer signBoardClearancePassed;		// 标志板垂直度与净空合格点
	private Integer signThickness;		// 标线厚度检测点
	private Integer signThicknessPassed;		// 标线厚度合格点
	private Integer signReverseReflection;		// 标线逆反射系数检测点
	private Integer signReverseReflectionPassed;		// 标线逆反射系数合格点
	private Integer trafficOthers;		// 交通安全设施其他检测点
	private Integer trafficOthersPassed;		// 交通安全设施其他合格点
	
	
	private Integer networkQuality;		// 网线质量检测点
	private Integer networkQualityPassed;		// 网线质量合格点
	private Integer coatingThickness;		// 涂层厚度检测点
	private Integer coatingThicknessPassed;		// 涂层厚度合格点
	private Integer insulationResistance;		// 绝缘电阻检测点
	private Integer insulationResistancePassed;		// 绝缘电阻合格点
	private Integer groundingResistance;		// 接地电阻检测点
	private Integer groundingResistancePassed;		// 接地电阻合格点
	private Integer powerFactors;		// 灯具功率因数检测点
	private Integer powerFactorsPassed;		// 灯具功率因数合格点
	private Integer blowerClearance;		// 风机安装净空检测点
	private Integer blowerClearancePassed;		// 风机安装净空合格点
	private Integer meOthers;		// 其他检测点
	private Integer meOthersPassed;		// 其他合格点
	        
	public QualityDataIndex() {
		super();
	}

	public QualityDataIndex(String id){
		super(id);
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public ProSimpleInfo getProSimpleInfo() {
		return proSimpleInfo;
	}

	public void setProSimpleInfo(ProSimpleInfo proSimpleInfo) {
		this.proSimpleInfo = proSimpleInfo;
	}
	
	public Integer getEarthworkCompaction() {
		return earthworkCompaction;
	}

	public void setEarthworkCompaction(Integer earthworkCompaction) {
		this.earthworkCompaction = earthworkCompaction;
	}
	
	public Integer getEarthworkCompactionPassed() {
		return earthworkCompactionPassed;
	}

	public void setEarthworkCompactionPassed(Integer earthworkCompactionPassed) {
		this.earthworkCompactionPassed = earthworkCompactionPassed;
	}
	
	public Integer getEarthworkDeflection() {
		return earthworkDeflection;
	}

	public void setEarthworkDeflection(Integer earthworkDeflection) {
		this.earthworkDeflection = earthworkDeflection;
	}
	
	public Integer getEarthworkDeflectionPassed() {
		return earthworkDeflectionPassed;
	}

	public void setEarthworkDeflectionPassed(Integer earthworkDeflectionPassed) {
		this.earthworkDeflectionPassed = earthworkDeflectionPassed;
	}
	
	public Integer getEarthworkAshDosage() {
		return earthworkAshDosage;
	}

	public void setEarthworkAshDosage(Integer earthworkAshDosage) {
		this.earthworkAshDosage = earthworkAshDosage;
	}
	
	public Integer getEarthworkAshDosagePassed() {
		return earthworkAshDosagePassed;
	}

	public void setEarthworkAshDosagePassed(Integer earthworkAshDosagePassed) {
		this.earthworkAshDosagePassed = earthworkAshDosagePassed;
	}
	
	public Integer getDrainageSectionSize() {
		return drainageSectionSize;
	}

	public void setDrainageSectionSize(Integer drainageSectionSize) {
		this.drainageSectionSize = drainageSectionSize;
	}
	
	public Integer getDrainageSectionSizePassed() {
		return drainageSectionSizePassed;
	}

	public void setDrainageSectionSizePassed(Integer drainageSectionSizePassed) {
		this.drainageSectionSizePassed = drainageSectionSizePassed;
	}
	
	public Integer getDrainagePavedThickness() {
		return drainagePavedThickness;
	}

	public void setDrainagePavedThickness(Integer drainagePavedThickness) {
		this.drainagePavedThickness = drainagePavedThickness;
	}
	
	public Integer getDrainagePavedThicknessPassed() {
		return drainagePavedThicknessPassed;
	}

	public void setDrainagePavedThicknessPassed(Integer drainagePavedThicknessPassed) {
		this.drainagePavedThicknessPassed = drainagePavedThicknessPassed;
	}
	
	public Integer getBridgeConcreteStrength() {
		return bridgeConcreteStrength;
	}

	public void setBridgeConcreteStrength(Integer bridgeConcreteStrength) {
		this.bridgeConcreteStrength = bridgeConcreteStrength;
	}
	
	public Integer getBridgeConcreteStrengthPassed() {
		return bridgeConcreteStrengthPassed;
	}

	public void setBridgeConcreteStrengthPassed(Integer bridgeConcreteStrengthPassed) {
		this.bridgeConcreteStrengthPassed = bridgeConcreteStrengthPassed;
	}
	
	public Integer getBridgeReinforcedThickness() {
		return bridgeReinforcedThickness;
	}

	public void setBridgeReinforcedThickness(Integer bridgeReinforcedThickness) {
		this.bridgeReinforcedThickness = bridgeReinforcedThickness;
	}

	public Integer getBridgeReinforcedThicknessPassed() {
		return bridgeReinforcedThicknessPassed;
	}

	public void setBridgeReinforcedThicknessPassed(Integer bridgeReinforcedThicknessPassed) {
		this.bridgeReinforcedThicknessPassed = bridgeReinforcedThicknessPassed;
	}

	public Integer getBridgeStructureSize() {
		return bridgeStructureSize;
	}

	public void setBridgeStructureSize(Integer bridgeStructureSize) {
		this.bridgeStructureSize = bridgeStructureSize;
	}
	
	public Integer getBridgeStructureSizePassed() {
		return bridgeStructureSizePassed;
	}

	public void setBridgeStructureSizePassed(Integer bridgeStructureSizePassed) {
		this.bridgeStructureSizePassed = bridgeStructureSizePassed;
	}
	
	public Integer getBridgeCulvertSpacing() {
		return bridgeCulvertSpacing;
	}

	public void setBridgeCulvertSpacing(Integer bridgeCulvertSpacing) {
		this.bridgeCulvertSpacing = bridgeCulvertSpacing;
	}
	
	public Integer getBridgeCulvertSpacingPassed() {
		return bridgeCulvertSpacingPassed;
	}

	public void setBridgeCulvertSpacingPassed(Integer bridgeCulvertSpacingPassed) {
		this.bridgeCulvertSpacingPassed = bridgeCulvertSpacingPassed;
	}
	
	public Integer getBridgeCulvertElevationPosition() {
		return bridgeCulvertElevationPosition;
	}

	public void setBridgeCulvertElevationPosition(Integer bridgeCulvertElevationPosition) {
		this.bridgeCulvertElevationPosition = bridgeCulvertElevationPosition;
	}
	
	public Integer getBridgeCulvertElevationPositionPassed() {
		return bridgeCulvertElevationPositionPassed;
	}

	public void setBridgeCulvertElevationPositionPassed(Integer bridgeCulvertElevationPositionPassed) {
		this.bridgeCulvertElevationPositionPassed = bridgeCulvertElevationPositionPassed;
	}
	
	public Integer getSupportConcreteStrength() {
		return supportConcreteStrength;
	}

	public void setSupportConcreteStrength(Integer supportConcreteStrength) {
		this.supportConcreteStrength = supportConcreteStrength;
	}
	
	public Integer getSupportConcreteStrengthPassed() {
		return supportConcreteStrengthPassed;
	}

	public void setSupportConcreteStrengthPassed(Integer supportConcreteStrengthPassed) {
		this.supportConcreteStrengthPassed = supportConcreteStrengthPassed;
	}
	
	public Integer getSupportSectionSize() {
		return supportSectionSize;
	}

	public void setSupportSectionSize(Integer supportSectionSize) {
		this.supportSectionSize = supportSectionSize;
	}
	
	public Integer getSupportSectionSizePassed() {
		return supportSectionSizePassed;
	}

	public void setSupportSectionSizePassed(Integer supportSectionSizePassed) {
		this.supportSectionSizePassed = supportSectionSizePassed;
	}
	
	public Integer getSubgradeEngineeringOthers() {
		return subgradeEngineeringOthers;
	}

	public void setSubgradeEngineeringOthers(Integer subgradeEngineeringOthers) {
		this.subgradeEngineeringOthers = subgradeEngineeringOthers;
	}
	
	public Integer getSubgradeEngineeringOthersPassed() {
		return subgradeEngineeringOthersPassed;
	}

	public void setSubgradeEngineeringOthersPassed(Integer subgradeEngineeringOthersPassed) {
		this.subgradeEngineeringOthersPassed = subgradeEngineeringOthersPassed;
	}
	
	public Integer getSurfaceCompaction() {
		return surfaceCompaction;
	}

	public void setSurfaceCompaction(Integer surfaceCompaction) {
		this.surfaceCompaction = surfaceCompaction;
	}
	
	public Integer getSurfaceCompactionPassed() {
		return surfaceCompactionPassed;
	}

	public void setSurfaceCompactionPassed(Integer surfaceCompactionPassed) {
		this.surfaceCompactionPassed = surfaceCompactionPassed;
	}
	
	public Integer getSurfaceConcreteRoadStrength() {
		return surfaceConcreteRoadStrength;
	}

	public void setSurfaceConcreteRoadStrength(Integer surfaceConcreteRoadStrength) {
		this.surfaceConcreteRoadStrength = surfaceConcreteRoadStrength;
	}
	
	public Integer getSurfaceConcreteRoadStrengthPassed() {
		return surfaceConcreteRoadStrengthPassed;
	}

	public void setSurfaceConcreteRoadStrengthPassed(Integer surfaceConcreteRoadStrengthPassed) {
		this.surfaceConcreteRoadStrengthPassed = surfaceConcreteRoadStrengthPassed;
	}
	
	public Integer getSurfaceDeflection() {
		return surfaceDeflection;
	}

	public void setSurfaceDeflection(Integer surfaceDeflection) {
		this.surfaceDeflection = surfaceDeflection;
	}
	
	public Integer getSurfaceDeflectionPassed() {
		return surfaceDeflectionPassed;
	}

	public void setSurfaceDeflectionPassed(Integer surfaceDeflectionPassed) {
		this.surfaceDeflectionPassed = surfaceDeflectionPassed;
	}
	
	public Integer getSurfaceAsphaltContent() {
		return surfaceAsphaltContent;
	}

	public void setSurfaceAsphaltContent(Integer surfaceAsphaltContent) {
		this.surfaceAsphaltContent = surfaceAsphaltContent;
	}
	
	public Integer getSurfaceAsphaltContentPassed() {
		return surfaceAsphaltContentPassed;
	}

	public void setSurfaceAsphaltContentPassed(Integer surfaceAsphaltContentPassed) {
		this.surfaceAsphaltContentPassed = surfaceAsphaltContentPassed;
	}
	
	public Integer getSurfacePorosity() {
		return surfacePorosity;
	}

	public void setSurfacePorosity(Integer surfacePorosity) {
		this.surfacePorosity = surfacePorosity;
	}
	
	public Integer getSurfacePorosityPassed() {
		return surfacePorosityPassed;
	}

	public void setSurfacePorosityPassed(Integer surfacePorosityPassed) {
		this.surfacePorosityPassed = surfacePorosityPassed;
	}
	
	public Integer getSurfaceThickness() {
		return surfaceThickness;
	}

	public void setSurfaceThickness(Integer surfaceThickness) {
		this.surfaceThickness = surfaceThickness;
	}
	
	public Integer getSurfaceThicknessPassed() {
		return surfaceThicknessPassed;
	}

	public void setSurfaceThicknessPassed(Integer surfaceThicknessPassed) {
		this.surfaceThicknessPassed = surfaceThicknessPassed;
	}
	
	public Integer getSurfaceFlatness() {
		return surfaceFlatness;
	}

	public void setSurfaceFlatness(Integer surfaceFlatness) {
		this.surfaceFlatness = surfaceFlatness;
	}
	
	public Integer getSurfaceFlatnessPassed() {
		return surfaceFlatnessPassed;
	}

	public void setSurfaceFlatnessPassed(Integer surfaceFlatnessPassed) {
		this.surfaceFlatnessPassed = surfaceFlatnessPassed;
	}
	
	public Integer getSurfaceCrossSlope() {
		return surfaceCrossSlope;
	}

	public void setSurfaceCrossSlope(Integer surfaceCrossSlope) {
		this.surfaceCrossSlope = surfaceCrossSlope;
	}
	
	public Integer getSurfaceCrossSlopePassed() {
		return surfaceCrossSlopePassed;
	}

	public void setSurfaceCrossSlopePassed(Integer surfaceCrossSlopePassed) {
		this.surfaceCrossSlopePassed = surfaceCrossSlopePassed;
	}
	
	public Integer getSurfaceElevation() {
		return surfaceElevation;
	}

	public void setSurfaceElevation(Integer surfaceElevation) {
		this.surfaceElevation = surfaceElevation;
	}
	
	public Integer getSurfaceElevationPassed() {
		return surfaceElevationPassed;
	}

	public void setSurfaceElevationPassed(Integer surfaceElevationPassed) {
		this.surfaceElevationPassed = surfaceElevationPassed;
	}
	
	public Integer getSurfaceWaterSeepageCoefficient() {
		return surfaceWaterSeepageCoefficient;
	}

	public void setSurfaceWaterSeepageCoefficient(Integer surfaceWaterSeepageCoefficient) {
		this.surfaceWaterSeepageCoefficient = surfaceWaterSeepageCoefficient;
	}
	
	public Integer getSurfaceWaterSeepageCoefficientPassed() {
		return surfaceWaterSeepageCoefficientPassed;
	}

	public void setSurfaceWaterSeepageCoefficientPassed(Integer surfaceWaterSeepageCoefficientPassed) {
		this.surfaceWaterSeepageCoefficientPassed = surfaceWaterSeepageCoefficientPassed;
	}
	
	public Integer getSurfaceSlabHeightDifference() {
		return surfaceSlabHeightDifference;
	}

	public void setSurfaceSlabHeightDifference(Integer surfaceSlabHeightDifference) {
		this.surfaceSlabHeightDifference = surfaceSlabHeightDifference;
	}
	
	public Integer getSurfaceSlabHeightDifferencePassed() {
		return surfaceSlabHeightDifferencePassed;
	}

	public void setSurfaceSlabHeightDifferencePassed(Integer surfaceSlabHeightDifferencePassed) {
		this.surfaceSlabHeightDifferencePassed = surfaceSlabHeightDifferencePassed;
	}
	
	public Integer getSurfaceAntiSlipSurface() {
		return surfaceAntiSlipSurface;
	}

	public void setSurfaceAntiSlipSurface(Integer surfaceAntiSlipSurface) {
		this.surfaceAntiSlipSurface = surfaceAntiSlipSurface;
	}
	
	public Integer getSurfaceAntiSlipSurfacePassed() {
		return surfaceAntiSlipSurfacePassed;
	}

	public void setSurfaceAntiSlipSurfacePassed(Integer surfaceAntiSlipSurfacePassed) {
		this.surfaceAntiSlipSurfacePassed = surfaceAntiSlipSurfacePassed;
	}
	
	public Integer getGrassrootsLevelCompaction() {
		return grassrootsLevelCompaction;
	}

	public void setGrassrootsLevelCompaction(Integer grassrootsLevelCompaction) {
		this.grassrootsLevelCompaction = grassrootsLevelCompaction;
	}
	
	public Integer getGrassrootsLevelCompactionPassed() {
		return grassrootsLevelCompactionPassed;
	}

	public void setGrassrootsLevelCompactionPassed(Integer grassrootsLevelCompactionPassed) {
		this.grassrootsLevelCompactionPassed = grassrootsLevelCompactionPassed;
	}
	
	public Integer getGrassrootsSubbaseGrayAmount() {
		return grassrootsSubbaseGrayAmount;
	}

	public void setGrassrootsSubbaseGrayAmount(Integer grassrootsSubbaseGrayAmount) {
		this.grassrootsSubbaseGrayAmount = grassrootsSubbaseGrayAmount;
	}
	
	public Integer getGrassrootsSubbaseGrayAmountPassed() {
		return grassrootsSubbaseGrayAmountPassed;
	}

	public void setGrassrootsSubbaseGrayAmountPassed(Integer grassrootsSubbaseGrayAmountPassed) {
		this.grassrootsSubbaseGrayAmountPassed = grassrootsSubbaseGrayAmountPassed;
	}
	
	public Integer getGrassrootsThickness() {
		return grassrootsThickness;
	}

	public void setGrassrootsThickness(Integer grassrootsThickness) {
		this.grassrootsThickness = grassrootsThickness;
	}
	
	public Integer getGrassrootsThicknessPassed() {
		return grassrootsThicknessPassed;
	}

	public void setGrassrootsThicknessPassed(Integer grassrootsThicknessPassed) {
		this.grassrootsThicknessPassed = grassrootsThicknessPassed;
	}
	
	public Integer getGrassrootsUnitIntegrity() {
		return grassrootsUnitIntegrity;
	}

	public void setGrassrootsUnitIntegrity(Integer grassrootsUnitIntegrity) {
		this.grassrootsUnitIntegrity = grassrootsUnitIntegrity;
	}
	
	public Integer getGrassrootsUnitIntegrityPassed() {
		return grassrootsUnitIntegrityPassed;
	}

	public void setGrassrootsUnitIntegrityPassed(Integer grassrootsUnitIntegrityPassed) {
		this.grassrootsUnitIntegrityPassed = grassrootsUnitIntegrityPassed;
	}
	
	public Integer getGrassrootsIntensity() {
		return grassrootsIntensity;
	}

	public void setGrassrootsIntensity(Integer grassrootsIntensity) {
		this.grassrootsIntensity = grassrootsIntensity;
	}
	
	public Integer getGrassrootsIntensityPassed() {
		return grassrootsIntensityPassed;
	}

	public void setGrassrootsIntensityPassed(Integer grassrootsIntensityPassed) {
		this.grassrootsIntensityPassed = grassrootsIntensityPassed;
	}
	
	public Integer getGrassrootsLevelCracks() {
		return grassrootsLevelCracks;
	}

	public void setGrassrootsLevelCracks(Integer grassrootsLevelCracks) {
		this.grassrootsLevelCracks = grassrootsLevelCracks;
	}
	
	public Integer getGrassrootsLevelCracksPassed() {
		return grassrootsLevelCracksPassed;
	}

	public void setGrassrootsLevelCracksPassed(Integer grassrootsLevelCracksPassed) {
		this.grassrootsLevelCracksPassed = grassrootsLevelCracksPassed;
	}
	
	public Integer getGrassrootsCrossSlope() {
		return grassrootsCrossSlope;
	}

	public void setGrassrootsCrossSlope(Integer grassrootsCrossSlope) {
		this.grassrootsCrossSlope = grassrootsCrossSlope;
	}
	
	public Integer getGrassrootsCrossSlopePassed() {
		return grassrootsCrossSlopePassed;
	}

	public void setGrassrootsCrossSlopePassed(Integer grassrootsCrossSlopePassed) {
		this.grassrootsCrossSlopePassed = grassrootsCrossSlopePassed;
	}
	
	public Integer getGrassrootsElevation() {
		return grassrootsElevation;
	}

	public void setGrassrootsElevation(Integer grassrootsElevation) {
		this.grassrootsElevation = grassrootsElevation;
	}
	
	public Integer getGrassrootsElevationPassed() {
		return grassrootsElevationPassed;
	}

	public void setGrassrootsElevationPassed(Integer grassrootsElevationPassed) {
		this.grassrootsElevationPassed = grassrootsElevationPassed;
	}
	
	public Integer getPavementEngineeringOthers() {
		return pavementEngineeringOthers;
	}

	public void setPavementEngineeringOthers(Integer pavementEngineeringOthers) {
		this.pavementEngineeringOthers = pavementEngineeringOthers;
	}
	
	public Integer getPavementEngineeringOthersPassed() {
		return pavementEngineeringOthersPassed;
	}

	public void setPavementEngineeringOthersPassed(Integer pavementEngineeringOthersPassed) {
		this.pavementEngineeringOthersPassed = pavementEngineeringOthersPassed;
	}
	
	public Integer getLowerstructureConcreteStrength() {
		return lowerstructureConcreteStrength;
	}

	public void setLowerstructureConcreteStrength(Integer lowerstructureConcreteStrength) {
		this.lowerstructureConcreteStrength = lowerstructureConcreteStrength;
	}
	
	public Integer getLowerstructureConcreteStrengthPassed() {
		return lowerstructureConcreteStrengthPassed;
	}

	public void setLowerstructureConcreteStrengthPassed(Integer lowerstructureConcreteStrengthPassed) {
		this.lowerstructureConcreteStrengthPassed = lowerstructureConcreteStrengthPassed;
	}
	
	public Integer getLowerstructureReinforcedProtectiveThickness() {
		return lowerstructureReinforcedProtectiveThickness;
	}

	public void setLowerstructureReinforcedProtectiveThickness(Integer lowerstructureReinforcedProtectiveThickness) {
		this.lowerstructureReinforcedProtectiveThickness = lowerstructureReinforcedProtectiveThickness;
	}
	
	public Integer getLowerstructureReinforcedProtectiveThicknessPassed() {
		return lowerstructureReinforcedProtectiveThicknessPassed;
	}

	public void setLowerstructureReinforcedProtectiveThicknessPassed(Integer lowerstructureReinforcedProtectiveThicknessPassed) {
		this.lowerstructureReinforcedProtectiveThicknessPassed = lowerstructureReinforcedProtectiveThicknessPassed;
	}
	
	public Integer getLowerstructurePierVerticality() {
		return lowerstructurePierVerticality;
	}

	public void setLowerstructurePierVerticality(Integer lowerstructurePierVerticality) {
		this.lowerstructurePierVerticality = lowerstructurePierVerticality;
	}
	
	public Integer getLowerstructurePierVerticalityPassed() {
		return lowerstructurePierVerticalityPassed;
	}

	public void setLowerstructurePierVerticalityPassed(Integer lowerstructurePierVerticalityPassed) {
		this.lowerstructurePierVerticalityPassed = lowerstructurePierVerticalityPassed;
	}
	
	public Integer getLowerstructureStructureSize() {
		return lowerstructureStructureSize;
	}

	public void setLowerstructureStructureSize(Integer lowerstructureStructureSize) {
		this.lowerstructureStructureSize = lowerstructureStructureSize;
	}
	
	public Integer getLowerstructureStructureSizePassed() {
		return lowerstructureStructureSizePassed;
	}

	public void setLowerstructureStructureSizePassed(Integer lowerstructureStructureSizePassed) {
		this.lowerstructureStructureSizePassed = lowerstructureStructureSizePassed;
	}
	
	public Integer getLowerstructureReinforcedSpacing() {
		return lowerstructureReinforcedSpacing;
	}

	public void setLowerstructureReinforcedSpacing(Integer lowerstructureReinforcedSpacing) {
		this.lowerstructureReinforcedSpacing = lowerstructureReinforcedSpacing;
	}
	
	public Integer getLowerstructureReinforcedSpacingPassed() {
		return lowerstructureReinforcedSpacingPassed;
	}

	public void setLowerstructureReinforcedSpacingPassed(Integer lowerstructureReinforcedSpacingPassed) {
		this.lowerstructureReinforcedSpacingPassed = lowerstructureReinforcedSpacingPassed;
	}
	
	public Integer getLowerstructureElevationPlaneLocation() {
		return lowerstructureElevationPlaneLocation;
	}

	public void setLowerstructureElevationPlaneLocation(Integer lowerstructureElevationPlaneLocation) {
		this.lowerstructureElevationPlaneLocation = lowerstructureElevationPlaneLocation;
	}
	
	public Integer getLowerstructureElevationPlaneLocationPassed() {
		return lowerstructureElevationPlaneLocationPassed;
	}

	public void setLowerstructureElevationPlaneLocationPassed(Integer lowerstructureElevationPlaneLocationPassed) {
		this.lowerstructureElevationPlaneLocationPassed = lowerstructureElevationPlaneLocationPassed;
	}
	
	public Integer getSuperstructureConcreteStrength() {
		return superstructureConcreteStrength;
	}

	public void setSuperstructureConcreteStrength(Integer superstructureConcreteStrength) {
		this.superstructureConcreteStrength = superstructureConcreteStrength;
	}
	
	public Integer getSuperstructureConcreteStrengthPassed() {
		return superstructureConcreteStrengthPassed;
	}

	public void setSuperstructureConcreteStrengthPassed(Integer superstructureConcreteStrengthPassed) {
		this.superstructureConcreteStrengthPassed = superstructureConcreteStrengthPassed;
	}
	
	public Integer getSuperstructureReinforcedProtectiveThickness() {
		return superstructureReinforcedProtectiveThickness;
	}

	public void setSuperstructureReinforcedProtectiveThickness(Integer superstructureReinforcedProtectiveThickness) {
		this.superstructureReinforcedProtectiveThickness = superstructureReinforcedProtectiveThickness;
	}

	public Integer getSuperstructureReinforcedProtectiveThicknessPassed() {
		return superstructureReinforcedProtectiveThicknessPassed;
	}

	public void setSuperstructureReinforcedProtectiveThicknessPassed(Integer superstructureReinforcedProtectiveThicknessPassed) {
		this.superstructureReinforcedProtectiveThicknessPassed = superstructureReinforcedProtectiveThicknessPassed;
	}
	
	public Integer getSuperstructureStructureSize() {
		return superstructureStructureSize;
	}

	public void setSuperstructureStructureSize(Integer superstructureStructureSize) {
		this.superstructureStructureSize = superstructureStructureSize;
	}
	
	public Integer getSuperstructureStructureSizePassed() {
		return superstructureStructureSizePassed;
	}

	public void setSuperstructureStructureSizePassed(Integer superstructureStructureSizePassed) {
		this.superstructureStructureSizePassed = superstructureStructureSizePassed;
	}
	
	public Integer getSuperstructureReinforcedSpacing() {
		return superstructureReinforcedSpacing;
	}

	public void setSuperstructureReinforcedSpacing(Integer superstructureReinforcedSpacing) {
		this.superstructureReinforcedSpacing = superstructureReinforcedSpacing;
	}
	
	public Integer getSuperstructureReinforcedSpacingPassed() {
		return superstructureReinforcedSpacingPassed;
	}

	public void setSuperstructureReinforcedSpacingPassed(Integer superstructureReinforcedSpacingPassed) {
		this.superstructureReinforcedSpacingPassed = superstructureReinforcedSpacingPassed;
	}
	
	public Integer getSuperstructureElevationPlaneLocation() {
		return superstructureElevationPlaneLocation;
	}

	public void setSuperstructureElevationPlaneLocation(Integer superstructureElevationPlaneLocation) {
		this.superstructureElevationPlaneLocation = superstructureElevationPlaneLocation;
	}
	
	public Integer getSuperstructureElevationPlaneLocationPassed() {
		return superstructureElevationPlaneLocationPassed;
	}

	public void setSuperstructureElevationPlaneLocationPassed(Integer superstructureElevationPlaneLocationPassed) {
		this.superstructureElevationPlaneLocationPassed = superstructureElevationPlaneLocationPassed;
	}
	
	public Integer getSuperstructurePrestressedHoleCoordinates() {
		return superstructurePrestressedHoleCoordinates;
	}

	public void setSuperstructurePrestressedHoleCoordinates(Integer superstructurePrestressedHoleCoordinates) {
		this.superstructurePrestressedHoleCoordinates = superstructurePrestressedHoleCoordinates;
	}
	
	public Integer getSuperstructurePrestressedHoleCoordinatesPassed() {
		return superstructurePrestressedHoleCoordinatesPassed;
	}

	public void setSuperstructurePrestressedHoleCoordinatesPassed(Integer superstructurePrestressedHoleCoordinatesPassed) {
		this.superstructurePrestressedHoleCoordinatesPassed = superstructurePrestressedHoleCoordinatesPassed;
	}
	
	public Integer getBridgeWidth() {
		return bridgeWidth;
	}

	public void setBridgeWidth(Integer bridgeWidth) {
		this.bridgeWidth = bridgeWidth;
	}
	
	public Integer getBridgeWidthPassed() {
		return bridgeWidthPassed;
	}

	public void setBridgeWidthPassed(Integer bridgeWidthPassed) {
		this.bridgeWidthPassed = bridgeWidthPassed;
	}
	
	public Integer getBridgeThickness() {
		return bridgeThickness;
	}

	public void setBridgeThickness(Integer bridgeThickness) {
		this.bridgeThickness = bridgeThickness;
	}
	
	public Integer getBridgeThicknessPassed() {
		return bridgeThicknessPassed;
	}

	public void setBridgeThicknessPassed(Integer bridgeThicknessPassed) {
		this.bridgeThicknessPassed = bridgeThicknessPassed;
	}
	
	public Integer getBridgeSlope() {
		return bridgeSlope;
	}

	public void setBridgeSlope(Integer bridgeSlope) {
		this.bridgeSlope = bridgeSlope;
	}
	
	public Integer getBridgeSlopePassed() {
		return bridgeSlopePassed;
	}

	public void setBridgeSlopePassed(Integer bridgeSlopePassed) {
		this.bridgeSlopePassed = bridgeSlopePassed;
	}
	
	public Integer getHardenedConcreteChlorideIonContent() {
		return hardenedConcreteChlorideIonContent;
	}

	public void setHardenedConcreteChlorideIonContent(Integer hardenedConcreteChlorideIonContent) {
		this.hardenedConcreteChlorideIonContent = hardenedConcreteChlorideIonContent;
	}
	
	public Integer getHardenedConcreteChlorideIonContentPassed() {
		return hardenedConcreteChlorideIonContentPassed;
	}

	public void setHardenedConcreteChlorideIonContentPassed(Integer hardenedConcreteChlorideIonContentPassed) {
		this.hardenedConcreteChlorideIonContentPassed = hardenedConcreteChlorideIonContentPassed;
	}
	
	public Integer getHardenedConcreteAlkaliContent() {
		return hardenedConcreteAlkaliContent;
	}

	public void setHardenedConcreteAlkaliContent(Integer hardenedConcreteAlkaliContent) {
		this.hardenedConcreteAlkaliContent = hardenedConcreteAlkaliContent;
	}
	
	public Integer getHardenedConcreteAlkaliContentPassed() {
		return hardenedConcreteAlkaliContentPassed;
	}

	public void setHardenedConcreteAlkaliContentPassed(Integer hardenedConcreteAlkaliContentPassed) {
		this.hardenedConcreteAlkaliContentPassed = hardenedConcreteAlkaliContentPassed;
	}
	
	public Integer getConcreteAdditives() {
		return concreteAdditives;
	}

	public void setConcreteAdditives(Integer concreteAdditives) {
		this.concreteAdditives = concreteAdditives;
	}
	
	public Integer getConcreteAdditivesPassed() {
		return concreteAdditivesPassed;
	}

	public void setConcreteAdditivesPassed(Integer concreteAdditivesPassed) {
		this.concreteAdditivesPassed = concreteAdditivesPassed;
	}
	
	public Integer getAnchorsPrestressing() {
		return anchorsPrestressing;
	}

	public void setAnchorsPrestressing(Integer anchorsPrestressing) {
		this.anchorsPrestressing = anchorsPrestressing;
	}
	
	public Integer getAnchorsPrestressingPassed() {
		return anchorsPrestressingPassed;
	}

	public void setAnchorsPrestressingPassed(Integer anchorsPrestressingPassed) {
		this.anchorsPrestressingPassed = anchorsPrestressingPassed;
	}
	
	public Integer getPlateRubberBearingInstallationQuality() {
		return plateRubberBearingInstallationQuality;
	}

	public void setPlateRubberBearingInstallationQuality(Integer plateRubberBearingInstallationQuality) {
		this.plateRubberBearingInstallationQuality = plateRubberBearingInstallationQuality;
	}
	
	public Integer getPlateRubberBearingInstallationQualityPassed() {
		return plateRubberBearingInstallationQualityPassed;
	}

	public void setPlateRubberBearingInstallationQualityPassed(Integer plateRubberBearingInstallationQualityPassed) {
		this.plateRubberBearingInstallationQualityPassed = plateRubberBearingInstallationQualityPassed;
	}
	
	public Integer getDeckTopSurfaceElevation() {
		return deckTopSurfaceElevation;
	}

	public void setDeckTopSurfaceElevation(Integer deckTopSurfaceElevation) {
		this.deckTopSurfaceElevation = deckTopSurfaceElevation;
	}
	
	public Integer getDeckTopSurfaceElevationPassed() {
		return deckTopSurfaceElevationPassed;
	}

	public void setDeckTopSurfaceElevationPassed(Integer deckTopSurfaceElevationPassed) {
		this.deckTopSurfaceElevationPassed = deckTopSurfaceElevationPassed;
	}
	
	public Integer getCrackWidth() {
		return crackWidth;
	}

	public void setCrackWidth(Integer crackWidth) {
		this.crackWidth = crackWidth;
	}
	
	public Integer getCrackWidthPassed() {
		return crackWidthPassed;
	}

	public void setCrackWidthPassed(Integer crackWidthPassed) {
		this.crackWidthPassed = crackWidthPassed;
	}
	
	public Integer getPileFoundation() {
		return pileFoundation;
	}

	public void setPileFoundation(Integer pileFoundation) {
		this.pileFoundation = pileFoundation;
	}
	
	public Integer getPileFoundationPassed() {
		return pileFoundationPassed;
	}

	public void setPileFoundationPassed(Integer pileFoundationPassed) {
		this.pileFoundationPassed = pileFoundationPassed;
	}
	
	public Integer getBridgeEngineeringOthers() {
		return bridgeEngineeringOthers;
	}

	public void setBridgeEngineeringOthers(Integer bridgeEngineeringOthers) {
		this.bridgeEngineeringOthers = bridgeEngineeringOthers;
	}
	
	public Integer getBridgeEngineeringOthersPassed() {
		return bridgeEngineeringOthersPassed;
	}

	public void setBridgeEngineeringOthersPassed(Integer bridgeEngineeringOthersPassed) {
		this.bridgeEngineeringOthersPassed = bridgeEngineeringOthersPassed;
	}
	
	public Integer getLiningSupportConcreteStrength() {
		return liningSupportConcreteStrength;
	}

	public void setLiningSupportConcreteStrength(Integer liningSupportConcreteStrength) {
		this.liningSupportConcreteStrength = liningSupportConcreteStrength;
	}
	
	public Integer getLiningSupportConcreteStrengthPassed() {
		return liningSupportConcreteStrengthPassed;
	}

	public void setLiningSupportConcreteStrengthPassed(Integer liningSupportConcreteStrengthPassed) {
		this.liningSupportConcreteStrengthPassed = liningSupportConcreteStrengthPassed;
	}
	
	public Integer getLiningSupportLiningThickness() {
		return liningSupportLiningThickness;
	}

	public void setLiningSupportLiningThickness(Integer liningSupportLiningThickness) {
		this.liningSupportLiningThickness = liningSupportLiningThickness;
	}
	
	public Integer getLiningSupportLiningThicknessPassed() {
		return liningSupportLiningThicknessPassed;
	}

	public void setLiningSupportLiningThicknessPassed(Integer liningSupportLiningThicknessPassed) {
		this.liningSupportLiningThicknessPassed = liningSupportLiningThicknessPassed;
	}
	
	public Integer getLiningSupportFlatness() {
		return liningSupportFlatness;
	}

	public void setLiningSupportFlatness(Integer liningSupportFlatness) {
		this.liningSupportFlatness = liningSupportFlatness;
	}
	
	public Integer getLiningSupportFlatnessPassed() {
		return liningSupportFlatnessPassed;
	}

	public void setLiningSupportFlatnessPassed(Integer liningSupportFlatnessPassed) {
		this.liningSupportFlatnessPassed = liningSupportFlatnessPassed;
	}
	
	public Integer getLiningSupportAnchorDrawing() {
		return liningSupportAnchorDrawing;
	}

	public void setLiningSupportAnchorDrawing(Integer liningSupportAnchorDrawing) {
		this.liningSupportAnchorDrawing = liningSupportAnchorDrawing;
	}
	
	public Integer getLiningSupportAnchorDrawingPassed() {
		return liningSupportAnchorDrawingPassed;
	}

	public void setLiningSupportAnchorDrawingPassed(Integer liningSupportAnchorDrawingPassed) {
		this.liningSupportAnchorDrawingPassed = liningSupportAnchorDrawingPassed;
	}
	
	public Integer getAnchorSpacingLengthGrouting() {
		return anchorSpacingLengthGrouting;
	}

	public void setAnchorSpacingLengthGrouting(Integer anchorSpacingLengthGrouting) {
		this.anchorSpacingLengthGrouting = anchorSpacingLengthGrouting;
	}
	
	public Integer getAnchorSpacingLengthGroutingPassed() {
		return anchorSpacingLengthGroutingPassed;
	}

	public void setAnchorSpacingLengthGroutingPassed(Integer anchorSpacingLengthGroutingPassed) {
		this.anchorSpacingLengthGroutingPassed = anchorSpacingLengthGroutingPassed;
	}
	
	public Integer getClearance() {
		return clearance;
	}

	public void setClearance(Integer clearance) {
		this.clearance = clearance;
	}
	
	public Integer getClearancePassed() {
		return clearancePassed;
	}

	public void setClearancePassed(Integer clearancePassed) {
		this.clearancePassed = clearancePassed;
	}
	
	public Integer getSectionOutline() {
		return sectionOutline;
	}

	public void setSectionOutline(Integer sectionOutline) {
		this.sectionOutline = sectionOutline;
	}
	
	public Integer getSectionOutlinePassed() {
		return sectionOutlinePassed;
	}

	public void setSectionOutlinePassed(Integer sectionOutlinePassed) {
		this.sectionOutlinePassed = sectionOutlinePassed;
	}
	
	public Integer getArchesSpacing() {
		return archesSpacing;
	}

	public void setArchesSpacing(Integer archesSpacing) {
		this.archesSpacing = archesSpacing;
	}
	
	public Integer getArchesSpacingPassed() {
		return archesSpacingPassed;
	}

	public void setArchesSpacingPassed(Integer archesSpacingPassed) {
		this.archesSpacingPassed = archesSpacingPassed;
	}
	
	public Integer getReinforcedSpacing() {
		return reinforcedSpacing;
	}

	public void setReinforcedSpacing(Integer reinforcedSpacing) {
		this.reinforcedSpacing = reinforcedSpacing;
	}
	
	public Integer getReinforcedSpacingPassed() {
		return reinforcedSpacingPassed;
	}

	public void setReinforcedSpacingPassed(Integer reinforcedSpacingPassed) {
		this.reinforcedSpacingPassed = reinforcedSpacingPassed;
	}
	
	public Integer getEmpties() {
		return empties;
	}

	public void setEmpties(Integer empties) {
		this.empties = empties;
	}
	
	public Integer getEmptiesPassed() {
		return emptiesPassed;
	}

	public void setEmptiesPassed(Integer emptiesPassed) {
		this.emptiesPassed = emptiesPassed;
	}
	
	public Integer getWaterproofBoardWeldingWide() {
		return waterproofBoardWeldingWide;
	}

	public void setWaterproofBoardWeldingWide(Integer waterproofBoardWeldingWide) {
		this.waterproofBoardWeldingWide = waterproofBoardWeldingWide;
	}
	
	public Integer getWaterproofBoardWeldingWidePassed() {
		return waterproofBoardWeldingWidePassed;
	}

	public void setWaterproofBoardWeldingWidePassed(Integer waterproofBoardWeldingWidePassed) {
		this.waterproofBoardWeldingWidePassed = waterproofBoardWeldingWidePassed;
	}
	
	public Integer getLeadSmallNumIndirect() {
		return leadSmallNumIndirect;
	}

	public void setLeadSmallNumIndirect(Integer leadSmallNumIndirect) {
		this.leadSmallNumIndirect = leadSmallNumIndirect;
	}
	
	public Integer getLeadSmallNumIndirectPassed() {
		return leadSmallNumIndirectPassed;
	}

	public void setLeadSmallNumIndirectPassed(Integer leadSmallNumIndirectPassed) {
		this.leadSmallNumIndirectPassed = leadSmallNumIndirectPassed;
	}
	
	public Integer getTunnelPavement() {
		return tunnelPavement;
	}

	public void setTunnelPavement(Integer tunnelPavement) {
		this.tunnelPavement = tunnelPavement;
	}
	
	public Integer getTunnelPavementPassed() {
		return tunnelPavementPassed;
	}

	public void setTunnelPavementPassed(Integer tunnelPavementPassed) {
		this.tunnelPavementPassed = tunnelPavementPassed;
	}
	
	public Integer getTunnelEngineeringOthers() {
		return tunnelEngineeringOthers;
	}

	public void setTunnelEngineeringOthers(Integer tunnelEngineeringOthers) {
		this.tunnelEngineeringOthers = tunnelEngineeringOthers;
	}
	
	public Integer getTunnelEngineeringOthersPassed() {
		return tunnelEngineeringOthersPassed;
	}

	public void setTunnelEngineeringOthersPassed(Integer tunnelEngineeringOthersPassed) {
		this.tunnelEngineeringOthersPassed = tunnelEngineeringOthersPassed;
	}
	
	public Integer getReinforced() {
		return reinforced;
	}

	public void setReinforced(Integer reinforced) {
		this.reinforced = reinforced;
	}
	
	public Integer getReinforcedPassed() {
		return reinforcedPassed;
	}

	public void setReinforcedPassed(Integer reinforcedPassed) {
		this.reinforcedPassed = reinforcedPassed;
	}
	
	public Integer getCement() {
		return cement;
	}

	public void setCement(Integer cement) {
		this.cement = cement;
	}
	
	public Integer getCementPassed() {
		return cementPassed;
	}

	public void setCementPassed(Integer cementPassed) {
		this.cementPassed = cementPassed;
	}
	
	public Integer getAsphalt() {
		return asphalt;
	}

	public void setAsphalt(Integer asphalt) {
		this.asphalt = asphalt;
	}
	
	public Integer getAsphaltPassed() {
		return asphaltPassed;
	}

	public void setAsphaltPassed(Integer asphaltPassed) {
		this.asphaltPassed = asphaltPassed;
	}
	
	public Integer getLime() {
		return lime;
	}

	public void setLime(Integer lime) {
		this.lime = lime;
	}
	
	public Integer getLimePassed() {
		return limePassed;
	}

	public void setLimePassed(Integer limePassed) {
		this.limePassed = limePassed;
	}
	
	public Integer getGravel() {
		return gravel;
	}

	public void setGravel(Integer gravel) {
		this.gravel = gravel;
	}
	
	public Integer getGravelsandPassed() {
		return gravelsandPassed;
	}

	public void setGravelsandPassed(Integer gravelsandPassed) {
		this.gravelsandPassed = gravelsandPassed;
	}
	
	public Integer getSand() {
		return sand;
	}

	public void setSand(Integer sand) {
		this.sand = sand;
	}
	
	public Integer getSandPassed() {
		return sandPassed;
	}

	public void setSandPassed(Integer sandPassed) {
		this.sandPassed = sandPassed;
	}
	
	public Integer getMineralPowder() {
		return mineralPowder;
	}

	public void setMineralPowder(Integer mineralPowder) {
		this.mineralPowder = mineralPowder;
	}
	
	public Integer getMineralPowderPassed() {
		return mineralPowderPassed;
	}

	public void setMineralPowderPassed(Integer mineralPowderPassed) {
		this.mineralPowderPassed = mineralPowderPassed;
	}
	
	public Integer getRubberBearings() {
		return rubberBearings;
	}

	public void setRubberBearings(Integer rubberBearings) {
		this.rubberBearings = rubberBearings;
	}
	
	public Integer getRubberBearingsPassed() {
		return rubberBearingsPassed;
	}

	public void setRubberBearingsPassed(Integer rubberBearingsPassed) {
		this.rubberBearingsPassed = rubberBearingsPassed;
	}
	
	public Integer getAnchor() {
		return anchor;
	}

	public void setAnchor(Integer anchor) {
		this.anchor = anchor;
	}
	
	public Integer getAnchorPassed() {
		return anchorPassed;
	}

	public void setAnchorPassed(Integer anchorPassed) {
		this.anchorPassed = anchorPassed;
	}
	
	public Integer getSplicingBolts() {
		return splicingBolts;
	}

	public void setSplicingBolts(Integer splicingBolts) {
		this.splicingBolts = splicingBolts;
	}
	
	public Integer getSplicingBoltsPassed() {
		return splicingBoltsPassed;
	}

	public void setSplicingBoltsPassed(Integer splicingBoltsPassed) {
		this.splicingBoltsPassed = splicingBoltsPassed;
	}
	
	public Integer getGeotextile() {
		return geotextile;
	}

	public void setGeotextile(Integer geotextile) {
		this.geotextile = geotextile;
	}
	
	public Integer getGeotextilePassed() {
		return geotextilePassed;
	}

	public void setGeotextilePassed(Integer geotextilePassed) {
		this.geotextilePassed = geotextilePassed;
	}
	
	public Integer getWaterproofBoard() {
		return waterproofBoard;
	}

	public void setWaterproofBoard(Integer waterproofBoard) {
		this.waterproofBoard = waterproofBoard;
	}
	
	public Integer getWaterproofBoardPassed() {
		return waterproofBoardPassed;
	}

	public void setWaterproofBoardPassed(Integer waterproofBoardPassed) {
		this.waterproofBoardPassed = waterproofBoardPassed;
	}
	
	public Integer getDrain() {
		return drain;
	}

	public void setDrain(Integer drain) {
		this.drain = drain;
	}
	
	public Integer getDrainPassed() {
		return drainPassed;
	}

	public void setDrainPassed(Integer drainPassed) {
		this.drainPassed = drainPassed;
	}
	
	public Integer getSlurry() {
		return slurry;
	}

	public void setSlurry(Integer slurry) {
		this.slurry = slurry;
	}
	
	public Integer getSlurryPassed() {
		return slurryPassed;
	}

	public void setSlurryPassed(Integer slurryPassed) {
		this.slurryPassed = slurryPassed;
	}
	
	public Integer getMaterialsOthers() {
		return materialsOthers;
	}

	public void setMaterialsOthers(Integer materialsOthers) {
		this.materialsOthers = materialsOthers;
	}
	
	public Integer getMaterialsOthersPassed() {
		return materialsOthersPassed;
	}

	public void setMaterialsOthersPassed(Integer materialsOthersPassed) {
		this.materialsOthersPassed = materialsOthersPassed;
	}
	
	public Integer getComponentBaseThickness() {
		return componentBaseThickness;
	}

	public void setComponentBaseThickness(Integer componentBaseThickness) {
		this.componentBaseThickness = componentBaseThickness;
	}
	
	public Integer getComponentBaseThicknessPassed() {
		return componentBaseThicknessPassed;
	}

	public void setComponentBaseThicknessPassed(Integer componentBaseThicknessPassed) {
		this.componentBaseThicknessPassed = componentBaseThicknessPassed;
	}
	
	public Integer getComponentCoatingThickness() {
		return componentCoatingThickness;
	}

	public void setComponentCoatingThickness(Integer componentCoatingThickness) {
		this.componentCoatingThickness = componentCoatingThickness;
	}
	
	public Integer getComponentCoatingThicknessPassed() {
		return componentCoatingThicknessPassed;
	}

	public void setComponentCoatingThicknessPassed(Integer componentCoatingThicknessPassed) {
		this.componentCoatingThicknessPassed = componentCoatingThicknessPassed;
	}
	
	public Integer getFencebeamCenterHeight() {
		return fencebeamCenterHeight;
	}

	public void setFencebeamCenterHeight(Integer fencebeamCenterHeight) {
		this.fencebeamCenterHeight = fencebeamCenterHeight;
	}
	
	public Integer getFencebeamCenterHeightPassed() {
		return fencebeamCenterHeightPassed;
	}

	public void setFencebeamCenterHeightPassed(Integer fencebeamCenterHeightPassed) {
		this.fencebeamCenterHeightPassed = fencebeamCenterHeightPassed;
	}
	
	public Integer getFenceColumnEmbeddedDepth() {
		return fenceColumnEmbeddedDepth;
	}

	public void setFenceColumnEmbeddedDepth(Integer fenceColumnEmbeddedDepth) {
		this.fenceColumnEmbeddedDepth = fenceColumnEmbeddedDepth;
	}
	
	public Integer getFenceColumnEmbeddedDepthPassed() {
		return fenceColumnEmbeddedDepthPassed;
	}

	public void setFenceColumnEmbeddedDepthPassed(Integer fenceColumnEmbeddedDepthPassed) {
		this.fenceColumnEmbeddedDepthPassed = fenceColumnEmbeddedDepthPassed;
	}
	
	public Integer getStitchingBoltTensileStrength() {
		return stitchingBoltTensileStrength;
	}

	public void setStitchingBoltTensileStrength(Integer stitchingBoltTensileStrength) {
		this.stitchingBoltTensileStrength = stitchingBoltTensileStrength;
	}
	
	public Integer getStitchingBoltTensileStrengthPassed() {
		return stitchingBoltTensileStrengthPassed;
	}

	public void setStitchingBoltTensileStrengthPassed(Integer stitchingBoltTensileStrengthPassed) {
		this.stitchingBoltTensileStrengthPassed = stitchingBoltTensileStrengthPassed;
	}
	
	public Integer getPillarWallThickness() {
		return pillarWallThickness;
	}

	public void setPillarWallThickness(Integer pillarWallThickness) {
		this.pillarWallThickness = pillarWallThickness;
	}
	
	public Integer getPillarWallThicknessPassed() {
		return pillarWallThicknessPassed;
	}

	public void setPillarWallThicknessPassed(Integer pillarWallThicknessPassed) {
		this.pillarWallThicknessPassed = pillarWallThicknessPassed;
	}
	
	public Integer getConcreteFenceStrength() {
		return concreteFenceStrength;
	}

	public void setConcreteFenceStrength(Integer concreteFenceStrength) {
		this.concreteFenceStrength = concreteFenceStrength;
	}
	
	public Integer getConcreteFenceStrengthPassed() {
		return concreteFenceStrengthPassed;
	}

	public void setConcreteFenceStrengthPassed(Integer concreteFenceStrengthPassed) {
		this.concreteFenceStrengthPassed = concreteFenceStrengthPassed;
	}
	
	public Integer getWavePlateThickness() {
		return wavePlateThickness;
	}

	public void setWavePlateThickness(Integer wavePlateThickness) {
		this.wavePlateThickness = wavePlateThickness;
	}
	
	public Integer getWavePlateThicknessPassed() {
		return wavePlateThicknessPassed;
	}

	public void setWavePlateThicknessPassed(Integer wavePlateThicknessPassed) {
		this.wavePlateThicknessPassed = wavePlateThicknessPassed;
	}
	
	public Integer getSignBoardClearance() {
		return signBoardClearance;
	}

	public void setSignBoardClearance(Integer signBoardClearance) {
		this.signBoardClearance = signBoardClearance;
	}
	
	public Integer getSignBoardClearancePassed() {
		return signBoardClearancePassed;
	}

	public void setSignBoardClearancePassed(Integer signBoardClearancePassed) {
		this.signBoardClearancePassed = signBoardClearancePassed;
	}
	
	public Integer getSignThickness() {
		return signThickness;
	}

	public void setSignThickness(Integer signThickness) {
		this.signThickness = signThickness;
	}
	
	public Integer getSignThicknessPassed() {
		return signThicknessPassed;
	}

	public void setSignThicknessPassed(Integer signThicknessPassed) {
		this.signThicknessPassed = signThicknessPassed;
	}
	
	public Integer getSignReverseReflection() {
		return signReverseReflection;
	}

	public void setSignReverseReflection(Integer signReverseReflection) {
		this.signReverseReflection = signReverseReflection;
	}
	
	public Integer getSignReverseReflectionPassed() {
		return signReverseReflectionPassed;
	}

	public void setSignReverseReflectionPassed(Integer signReverseReflectionPassed) {
		this.signReverseReflectionPassed = signReverseReflectionPassed;
	}
	
	public Integer getTrafficOthers() {
		return trafficOthers;
	}

	public void setTrafficOthers(Integer trafficOthers) {
		this.trafficOthers = trafficOthers;
	}
	
	public Integer getTrafficOthersPassed() {
		return trafficOthersPassed;
	}

	public void setTrafficOthersPassed(Integer trafficOthersPassed) {
		this.trafficOthersPassed = trafficOthersPassed;
	}
	
	public Integer getNetworkQuality() {
		return networkQuality;
	}

	public void setNetworkQuality(Integer networkQuality) {
		this.networkQuality = networkQuality;
	}
	
	public Integer getNetworkQualityPassed() {
		return networkQualityPassed;
	}

	public void setNetworkQualityPassed(Integer networkQualityPassed) {
		this.networkQualityPassed = networkQualityPassed;
	}
	
	public Integer getCoatingThickness() {
		return coatingThickness;
	}

	public void setCoatingThickness(Integer coatingThickness) {
		this.coatingThickness = coatingThickness;
	}
	
	public Integer getCoatingThicknessPassed() {
		return coatingThicknessPassed;
	}

	public void setCoatingThicknessPassed(Integer coatingThicknessPassed) {
		this.coatingThicknessPassed = coatingThicknessPassed;
	}
	
	public Integer getInsulationResistance() {
		return insulationResistance;
	}

	public void setInsulationResistance(Integer insulationResistance) {
		this.insulationResistance = insulationResistance;
	}
	
	public Integer getInsulationResistancePassed() {
		return insulationResistancePassed;
	}

	public void setInsulationResistancePassed(Integer insulationResistancePassed) {
		this.insulationResistancePassed = insulationResistancePassed;
	}
	
	public Integer getGroundingResistance() {
		return groundingResistance;
	}

	public void setGroundingResistance(Integer groundingResistance) {
		this.groundingResistance = groundingResistance;
	}
	
	public Integer getGroundingResistancePassed() {
		return groundingResistancePassed;
	}

	public void setGroundingResistancePassed(Integer groundingResistancePassed) {
		this.groundingResistancePassed = groundingResistancePassed;
	}
	
	public Integer getPowerFactors() {
		return powerFactors;
	}

	public void setPowerFactors(Integer powerFactors) {
		this.powerFactors = powerFactors;
	}
	
	public Integer getPowerFactorsPassed() {
		return powerFactorsPassed;
	}

	public void setPowerFactorsPassed(Integer powerFactorsPassed) {
		this.powerFactorsPassed = powerFactorsPassed;
	}
	
	public Integer getBlowerClearance() {
		return blowerClearance;
	}

	public void setBlowerClearance(Integer blowerClearance) {
		this.blowerClearance = blowerClearance;
	}
	
	public Integer getBlowerClearancePassed() {
		return blowerClearancePassed;
	}

	public void setBlowerClearancePassed(Integer blowerClearancePassed) {
		this.blowerClearancePassed = blowerClearancePassed;
	}
	
	public Integer getMeOthers() {
		return meOthers;
	}

	public void setMeOthers(Integer meOthers) {
		this.meOthers = meOthers;
	}
	
	public Integer getMeOthersPassed() {
		return meOthersPassed;
	}

	public void setMeOthersPassed(Integer meOthersPassed) {
		this.meOthersPassed = meOthersPassed;
	}
	
}