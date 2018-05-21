<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>质量检测统计数据管理</title>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx }/quality/qualityDataIndex/">质量检测统计数据列表</a></li>
		<li class="active"><a href="${ctx }/quality/qualityDataIndex/see?id=${qualityDataIndex.id}">质量检测统计数据查看</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="qualityDataIndex" action="" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>项目名称：</label> 
				<form:input path="proSimpleInfo.projectSimpleName" htmlEscape="false" maxlength="11" class="input-xlarge readonly" />
			</li>
			<li><label>项目标段：</label> 
				<form:input path="projectLabel" htmlEscape="false" maxlength="11" class="input-xlarge readonly"/>
			<li><label>检查时间：</label>
				<input type="text" value="${qualityDataIndex.checkDate }年&nbsp;${fns:getDictLabel(qualityDataIndex.checkDateSeason, 'check_date_season', '')}">
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message }" />
	
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<td colspan="3" style="text-align: center">抽查指标</td>
				<td style="text-align: center">检测点</td>
				<td style="text-align: center">合格点</td>
				<td style="text-align: center">合格率</td>
			</tr>
		</thead>
		<c:choose>
			<c:when test="${qualityDataIndex.id == null}">
			</c:when>
			<c:otherwise>
				<tbody>
					<!-- 路基工程start -->
					<tr>
						<td rowspan="14">路基工程</td>
						<td rowspan="3">土石方</td>
						<td>压实度*</td>
						<td>${qualityDataIndex.earthworkCompaction  }</td>
						<td>${qualityDataIndex.earthworkCompactionPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.earthworkCompaction == 0 ? 0 : 
							(qualityDataIndex.earthworkCompactionPassed 
							/ qualityDataIndex.earthworkCompaction) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>弯沉*</td>
						<td>${qualityDataIndex.earthworkDeflection }</td>
						<td>${qualityDataIndex.earthworkDeflectionPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.earthworkDeflection == 0 ? 0 : (qualityDataIndex.earthworkDeflection 
							/ qualityDataIndex.earthworkDeflectionPassed) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>灰剂量</td>
						<td>${qualityDataIndex.earthworkAshDosage }</td>
						<td>${qualityDataIndex.earthworkAshDosagePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.earthworkAshDosagePassed == 0 ? 0 :
							(qualityDataIndex.earthworkAshDosagePassed 
							/ qualityDataIndex.earthworkAshDosage) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td rowspan="2">排水</td>
						<td>断面尺寸</td>
						<td>${qualityDataIndex.drainageSectionSize }</td>
						<td>${qualityDataIndex.drainageSectionSizePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.drainageSectionSize == 0 ? 0 : (qualityDataIndex.drainageSectionSizePassed 
							/ qualityDataIndex.drainageSectionSize) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>铺砌厚度</td>
						<td>${qualityDataIndex.drainagePavedThickness }</td>
						<td>${qualityDataIndex.drainagePavedThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.drainagePavedThickness == 0 ? 0 :(qualityDataIndex.drainagePavedThicknessPassed 
							/ qualityDataIndex.drainagePavedThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td rowspan="5">小桥涵</td>
						<td>砼强度*</td>
						<td>${qualityDataIndex.bridgeConcreteStrength }</td>
						<td>${qualityDataIndex.bridgeConcreteStrengthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeConcreteStrength == 0 ? 0 :(qualityDataIndex.bridgeConcreteStrengthPassed 
							/ qualityDataIndex.bridgeConcreteStrength) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>钢筋保护层厚度</td>
						<td>${qualityDataIndex.bridgeReinforcedThickness }</td>
						<td>${qualityDataIndex.bridgeReinforcedThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeReinforcedThickness == 0 ? 0 : (qualityDataIndex.bridgeReinforcedThicknessPassed 
							/ qualityDataIndex.bridgeReinforcedThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>结构尺寸</td>
						<td>${qualityDataIndex.bridgeStructureSize }</td>
						<td>${qualityDataIndex.bridgeStructureSizePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeStructureSize == 0 ? 0 : (qualityDataIndex.bridgeStructureSizePassed 
							/ qualityDataIndex.bridgeStructureSize) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>钢筋间距</td>
						<td>${qualityDataIndex.bridgeCulvertSpacing }</td>
						<td>${qualityDataIndex.bridgeCulvertSpacingPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeCulvertSpacing == 0 ? 0 : (qualityDataIndex.bridgeCulvertSpacingPassed 
							/ qualityDataIndex.bridgeCulvertSpacing) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>高程及平面位置</td>
						<td>${qualityDataIndex.bridgeCulvertElevationPosition }</td>
						<td>${qualityDataIndex.bridgeCulvertElevationPositionPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeCulvertElevationPosition == 0 ? 0 : (qualityDataIndex.bridgeCulvertElevationPositionPassed 
							/ qualityDataIndex.bridgeCulvertElevationPosition) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td rowspan="2">支挡工程</td>
						<td>砼强度*</td>
						<td>${qualityDataIndex.supportConcreteStrength }</td>
						<td>${qualityDataIndex.supportConcreteStrengthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.supportConcreteStrength == 0 ? 0 : (qualityDataIndex.supportConcreteStrengthPassed 
							/ qualityDataIndex.supportConcreteStrength) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>断面尺寸*</td>
						<td>${qualityDataIndex.supportSectionSize }</td>
						<td>${qualityDataIndex.supportSectionSizePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.supportSectionSize == 0 ? 0 : (qualityDataIndex.supportSectionSizePassed 
							/ qualityDataIndex.supportSectionSize) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">其他</td>
						<td>${qualityDataIndex.subgradeEngineeringOthers }</td>
						<td>${qualityDataIndex.subgradeEngineeringOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.subgradeEngineeringOthers == 0 ? 0 : (qualityDataIndex.subgradeEngineeringOthersPassed 
							/ qualityDataIndex.subgradeEngineeringOthers) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">小计</td>
						<td>${qualityDataIndex.earthworkCompaction					
									+ qualityDataIndex.earthworkDeflection					
									+ qualityDataIndex.earthworkAshDosage						
									+ qualityDataIndex.drainageSectionSize					
									+ qualityDataIndex.drainagePavedThickness					
									+ qualityDataIndex.bridgeConcreteStrength					
									+ qualityDataIndex.bridgeReinforcedThickness				
									+ qualityDataIndex.bridgeStructureSize					
									+ qualityDataIndex.bridgeCulvertSpacing					
									+ qualityDataIndex.bridgeCulvertElevationPosition			
									+ qualityDataIndex.supportConcreteStrength				
									+ qualityDataIndex.supportSectionSize						
									+ qualityDataIndex.subgradeEngineeringOthers }</td>
						<td>${qualityDataIndex.earthworkCompactionPassed
									+ qualityDataIndex.earthworkDeflectionPassed
									+ qualityDataIndex.earthworkAshDosagePassed		
									+ qualityDataIndex.drainageSectionSizePassed
									+ qualityDataIndex.drainagePavedThicknessPassed	
									+ qualityDataIndex.bridgeConcreteStrengthPassed		
									+ qualityDataIndex.bridgeReinforcedThicknessPassed
									+ qualityDataIndex.bridgeStructureSizePassed
									+ qualityDataIndex.bridgeCulvertSpacingPassed
									+ qualityDataIndex.bridgeCulvertElevationPositionPassed	
									+ qualityDataIndex.supportConcreteStrengthPassed
									+ qualityDataIndex.supportSectionSizePassed	
									+ qualityDataIndex.subgradeEngineeringOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${(qualityDataIndex.earthworkCompaction					
									+ qualityDataIndex.earthworkDeflection					
									+ qualityDataIndex.earthworkAshDosage						
									+ qualityDataIndex.drainageSectionSize					
									+ qualityDataIndex.drainagePavedThickness					
									+ qualityDataIndex.bridgeConcreteStrength					
									+ qualityDataIndex.bridgeReinforcedThickness				
									+ qualityDataIndex.bridgeStructureSize					
									+ qualityDataIndex.bridgeCulvertSpacing					
									+ qualityDataIndex.bridgeCulvertElevationPosition			
									+ qualityDataIndex.supportConcreteStrength				
									+ qualityDataIndex.supportSectionSize						
									+ qualityDataIndex.subgradeEngineeringOthers) == 0 ? 0 :((qualityDataIndex.earthworkCompactionPassed
									+ qualityDataIndex.earthworkDeflectionPassed
									+ qualityDataIndex.earthworkAshDosagePassed		
									+ qualityDataIndex.drainageSectionSizePassed
									+ qualityDataIndex.drainagePavedThicknessPassed	
									+ qualityDataIndex.bridgeConcreteStrengthPassed		
									+ qualityDataIndex.bridgeReinforcedThicknessPassed
									+ qualityDataIndex.bridgeStructureSizePassed
									+ qualityDataIndex.bridgeCulvertSpacingPassed
									+ qualityDataIndex.bridgeCulvertElevationPositionPassed	
									+ qualityDataIndex.supportConcreteStrengthPassed
									+ qualityDataIndex.supportSectionSizePassed	
									+ qualityDataIndex.subgradeEngineeringOthersPassed )/(qualityDataIndex.earthworkCompaction					
									+ qualityDataIndex.earthworkDeflection					
									+ qualityDataIndex.earthworkAshDosage						
									+ qualityDataIndex.drainageSectionSize					
									+ qualityDataIndex.drainagePavedThickness					
									+ qualityDataIndex.bridgeConcreteStrength					
									+ qualityDataIndex.bridgeReinforcedThickness				
									+ qualityDataIndex.bridgeStructureSize					
									+ qualityDataIndex.bridgeCulvertSpacing					
									+ qualityDataIndex.bridgeCulvertElevationPosition			
									+ qualityDataIndex.supportConcreteStrength				
									+ qualityDataIndex.supportSectionSize						
									+ qualityDataIndex.subgradeEngineeringOthers)) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>

					<!-- 路面工程start -->
					<tr>
						<td rowspan="22">路面工程</td>
						<td rowspan="12">面层</td>
						<td>压实度*</td>
						<td>${qualityDataIndex.surfaceCompaction }</td>
						<td>${qualityDataIndex.surfaceCompactionPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.surfaceCompaction == 0 ? 0 : (qualityDataIndex.surfaceCompactionPassed							/ qualityDataIndex.surfaceCompaction) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>混凝土路面强度*</td>
						<td>${qualityDataIndex.surfaceConcreteRoadStrength }</td>
						<td>${qualityDataIndex.surfaceConcreteRoadStrengthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.surfaceConcreteRoadStrength == 0 ? 0 : (qualityDataIndex.surfaceConcreteRoadStrengthPassed 
							/ qualityDataIndex.surfaceConcreteRoadStrength) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>弯沉*</td>
						<td>${qualityDataIndex.surfaceDeflection }</td>
						<td>${qualityDataIndex.surfaceDeflectionPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.surfaceDeflection == 0 ? 0 : (qualityDataIndex.surfaceDeflectionPassed 
							/ qualityDataIndex.surfaceDeflection) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>沥青含量</td>
						<td>${qualityDataIndex.surfaceAsphaltContent }</td>
						<td>${qualityDataIndex.surfaceAsphaltContentPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeCulvertSpacing == 0 ? 0 : (qualityDataIndex.surfaceAsphaltContent 
							/ qualityDataIndex.surfaceAsphaltContentPassed) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>空隙率</td>
						<td>${qualityDataIndex.surfacePorosity }</td>
						<td>${qualityDataIndex.surfacePorosityPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeCulvertSpacing == 0 ? 0 : (qualityDataIndex.surfacePorosity 
							/ qualityDataIndex.surfacePorosityPassed) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>厚度*</td>
						<td>${qualityDataIndex.surfaceThickness }</td>
						<td>${qualityDataIndex.surfaceThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeCulvertSpacing == 0 ? 0 : (qualityDataIndex.surfaceThickness 
							/ qualityDataIndex.surfaceThicknessPassed) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>平整度*</td>
						<td>${qualityDataIndex.surfaceFlatness }</td>
						<td>${qualityDataIndex.surfaceFlatnessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeCulvertSpacing == 0 ? 0 : (qualityDataIndex.surfaceFlatness 
							/ qualityDataIndex.surfaceFlatnessPassed) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>横坡</td>
						<td>${qualityDataIndex.surfaceCrossSlope }</td>
						<td>${qualityDataIndex.surfaceCrossSlopePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.surfaceCrossSlope == 0 ? 0 : (qualityDataIndex.surfaceCrossSlopePassed 
							/ qualityDataIndex.surfaceCrossSlope) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>高程</td>
						<td>${qualityDataIndex.surfaceElevation }</td>
						<td>${qualityDataIndex.surfaceElevationPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.surfaceElevation == 0 ? 0 : (qualityDataIndex.surfaceElevationPassed 
							/ qualityDataIndex.surfaceElevation) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>渗水系数</td>
						<td>${qualityDataIndex.surfaceWaterSeepageCoefficient }</td>
						<td>${qualityDataIndex.surfaceWaterSeepageCoefficientPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.surfaceWaterSeepageCoefficient == 0 ? 0 : (qualityDataIndex.surfaceWaterSeepageCoefficientPassed 
							/ qualityDataIndex.surfaceWaterSeepageCoefficient) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>混凝土路面相邻板高差</td>
						<td>${qualityDataIndex.surfaceSlabHeightDifference }</td>
						<td>${qualityDataIndex.surfaceSlabHeightDifferencePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.surfaceSlabHeightDifference == 0 ? 0 : (qualityDataIndex.surfaceSlabHeightDifferencePassed 
							/ qualityDataIndex.surfaceSlabHeightDifference) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>路面抗滑</td>
						<td>${qualityDataIndex.surfaceAntiSlipSurface }</td>
						<td>${qualityDataIndex.surfaceAntiSlipSurfacePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.surfaceAntiSlipSurface == 0 ? 0 : (qualityDataIndex.surfaceAntiSlipSurfacePassed 
							/ qualityDataIndex.surfaceAntiSlipSurface) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td rowspan="8">基层底基层</td>
						<td>压实度*</td>
						<td>${qualityDataIndex.grassrootsLevelCompaction }</td>
						<td>${qualityDataIndex.grassrootsLevelCompactionPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.grassrootsLevelCompaction == 0 ? 0 : (qualityDataIndex.grassrootsLevelCompactionPassed 
							/ qualityDataIndex.grassrootsLevelCompaction) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>灰剂量</td>
						<td>${qualityDataIndex.grassrootsSubbaseGrayAmount }</td>
						<td>${qualityDataIndex.grassrootsSubbaseGrayAmountPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.grassrootsSubbaseGrayAmount == 0 ? 0 : (qualityDataIndex.grassrootsSubbaseGrayAmountPassed 
							/ qualityDataIndex.grassrootsSubbaseGrayAmount) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>厚度</td>
						<td>${qualityDataIndex.grassrootsThickness }</td>
						<td>${qualityDataIndex.grassrootsThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.grassrootsThickness == 0 ? 0 : (qualityDataIndex.grassrootsThicknessPassed 
							/ qualityDataIndex.grassrootsThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>整体性*</td>
						<td>${qualityDataIndex.grassrootsUnitIntegrity }</td>
						<td>${qualityDataIndex.grassrootsUnitIntegrityPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.grassrootsUnitIntegrity == 0 ? 0 : (qualityDataIndex.grassrootsUnitIntegrityPassed 
							/ qualityDataIndex.grassrootsUnitIntegrity) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>强度*</td>
						<td>${qualityDataIndex.grassrootsIntensity }</td>
						<td>${qualityDataIndex.grassrootsIntensityPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.grassrootsIntensity == 0 ? 0 : (qualityDataIndex.grassrootsIntensityPassed 
							/ qualityDataIndex.grassrootsIntensity) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>基层裂缝</td>
						<td>${qualityDataIndex.grassrootsLevelCracks }</td>
						<td>${qualityDataIndex.grassrootsLevelCracksPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.grassrootsLevelCracks == 0 ? 0 : (qualityDataIndex.grassrootsLevelCracksPassed 
							/ qualityDataIndex.grassrootsLevelCracks) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>横坡</td>
						<td>${qualityDataIndex.grassrootsCrossSlope }</td>
						<td>${qualityDataIndex.grassrootsCrossSlopePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.grassrootsCrossSlope == 0 ? 0 : (qualityDataIndex.grassrootsCrossSlopePassed 
							/ qualityDataIndex.grassrootsCrossSlope) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>高程</td>
						<td>${qualityDataIndex.grassrootsElevation }</td>
						<td>${qualityDataIndex.grassrootsElevationPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.grassrootsElevation == 0 ? 0 : (qualityDataIndex.grassrootsElevationPassed 
							/ qualityDataIndex.grassrootsElevation) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">其它</td>
						<td>${qualityDataIndex.pavementEngineeringOthers }</td>
						<td>${qualityDataIndex.pavementEngineeringOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.pavementEngineeringOthers == 0 ? 0 : (qualityDataIndex.pavementEngineeringOthersPassed 
							/ qualityDataIndex.pavementEngineeringOthers) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">小计</td>
						<td>${qualityDataIndex.surfaceCompaction
									+ qualityDataIndex.surfaceConcreteRoadStrength
									+ qualityDataIndex.surfaceDeflection
									+ qualityDataIndex.surfaceAsphaltContent
									+ qualityDataIndex.surfacePorosity
									+ qualityDataIndex.surfaceThickness
									+ qualityDataIndex.surfaceFlatness
									+ qualityDataIndex.surfaceCrossSlope
									+ qualityDataIndex.surfaceElevation
									+ qualityDataIndex.surfaceWaterSeepageCoefficient
									+ qualityDataIndex.surfaceSlabHeightDifference
									+ qualityDataIndex.surfaceAntiSlipSurface
									+ qualityDataIndex.grassrootsLevelCompaction
									+ qualityDataIndex.grassrootsSubbaseGrayAmount
									+ qualityDataIndex.grassrootsThickness
									+ qualityDataIndex.grassrootsUnitIntegrity
									+ qualityDataIndex.grassrootsIntensity
									+ qualityDataIndex.grassrootsLevelCracks
									+ qualityDataIndex.grassrootsCrossSlope
									+ qualityDataIndex.grassrootsElevation
									+ qualityDataIndex.pavementEngineeringOthers }</td>
						<td>${qualityDataIndex.surfaceCompactionPassed
									+ qualityDataIndex.surfaceConcreteRoadStrengthPassed
									+ qualityDataIndex.surfaceDeflectionPassed
									+ qualityDataIndex.surfaceAsphaltContentPassed
									+ qualityDataIndex.surfacePorosityPassed
									+ qualityDataIndex.surfaceThicknessPassed
									+ qualityDataIndex.surfaceFlatnessPassed
									+ qualityDataIndex.surfaceCrossSlopePassed
									+ qualityDataIndex.surfaceElevationPassed
									+ qualityDataIndex.surfaceWaterSeepageCoefficientPassed
									+ qualityDataIndex.surfaceSlabHeightDifferencePassed
									+ qualityDataIndex.surfaceAntiSlipSurfacePassed
									+ qualityDataIndex.grassrootsLevelCompactionPassed
									+ qualityDataIndex.grassrootsSubbaseGrayAmountPassed
									+ qualityDataIndex.grassrootsThicknessPassed
									+ qualityDataIndex.grassrootsUnitIntegrityPassed
									+ qualityDataIndex.grassrootsIntensityPassed
									+ qualityDataIndex.grassrootsLevelCracksPassed
									+ qualityDataIndex.grassrootsCrossSlopePassed
									+ qualityDataIndex.grassrootsElevationPassed
									+ qualityDataIndex.pavementEngineeringOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${(
									qualityDataIndex.surfaceCompaction
									+ qualityDataIndex.surfaceConcreteRoadStrength
									+ qualityDataIndex.surfaceDeflection
									+ qualityDataIndex.surfaceAsphaltContent
									+ qualityDataIndex.surfacePorosity
									+ qualityDataIndex.surfaceThickness
									+ qualityDataIndex.surfaceFlatness
									+ qualityDataIndex.surfaceCrossSlope
									+ qualityDataIndex.surfaceElevation
									+ qualityDataIndex.surfaceWaterSeepageCoefficient
									+ qualityDataIndex.surfaceSlabHeightDifference
									+ qualityDataIndex.surfaceAntiSlipSurface
									+ qualityDataIndex.grassrootsLevelCompaction
									+ qualityDataIndex.grassrootsSubbaseGrayAmount
									+ qualityDataIndex.grassrootsThickness
									+ qualityDataIndex.grassrootsUnitIntegrity
									+ qualityDataIndex.grassrootsIntensity
									+ qualityDataIndex.grassrootsLevelCracks
									+ qualityDataIndex.grassrootsCrossSlope
									+ qualityDataIndex.grassrootsElevation
									+ qualityDataIndex.pavementEngineeringOthers) == 0 ? 0 : ((
									qualityDataIndex.surfaceCompactionPassed
									+ qualityDataIndex.surfaceConcreteRoadStrengthPassed
									+ qualityDataIndex.surfaceDeflectionPassed
									+ qualityDataIndex.surfaceAsphaltContentPassed
									+ qualityDataIndex.surfacePorosityPassed
									+ qualityDataIndex.surfaceThicknessPassed
									+ qualityDataIndex.surfaceFlatnessPassed
									+ qualityDataIndex.surfaceCrossSlopePassed
									+ qualityDataIndex.surfaceElevationPassed
									+ qualityDataIndex.surfaceWaterSeepageCoefficientPassed
									+ qualityDataIndex.surfaceSlabHeightDifferencePassed
									+ qualityDataIndex.surfaceAntiSlipSurfacePassed
									+ qualityDataIndex.grassrootsLevelCompactionPassed
									+ qualityDataIndex.grassrootsSubbaseGrayAmountPassed
									+ qualityDataIndex.grassrootsThicknessPassed
									+ qualityDataIndex.grassrootsUnitIntegrityPassed
									+ qualityDataIndex.grassrootsIntensityPassed
									+ qualityDataIndex.grassrootsLevelCracksPassed
									+ qualityDataIndex.grassrootsCrossSlopePassed
									+ qualityDataIndex.grassrootsElevationPassed
									+ qualityDataIndex.pavementEngineeringOthersPassed )/(
									qualityDataIndex.surfaceCompaction
									+ qualityDataIndex.surfaceConcreteRoadStrength
									+ qualityDataIndex.surfaceDeflection
									+ qualityDataIndex.surfaceAsphaltContent
									+ qualityDataIndex.surfacePorosity
									+ qualityDataIndex.surfaceThickness
									+ qualityDataIndex.surfaceFlatness
									+ qualityDataIndex.surfaceCrossSlope
									+ qualityDataIndex.surfaceElevation
									+ qualityDataIndex.surfaceWaterSeepageCoefficient
									+ qualityDataIndex.surfaceSlabHeightDifference
									+ qualityDataIndex.surfaceAntiSlipSurface
									+ qualityDataIndex.grassrootsLevelCompaction
									+ qualityDataIndex.grassrootsSubbaseGrayAmount
									+ qualityDataIndex.grassrootsThickness
									+ qualityDataIndex.grassrootsUnitIntegrity
									+ qualityDataIndex.grassrootsIntensity
									+ qualityDataIndex.grassrootsLevelCracks
									+ qualityDataIndex.grassrootsCrossSlope
									+ qualityDataIndex.grassrootsElevation
									+ qualityDataIndex.pavementEngineeringOthers)) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>

					<!-- 桥梁工程start -->
					<tr>
						<td rowspan="25">桥梁工程</td>
						<td rowspan="6">下部结构</td>
						<td>墩台砼强度*</td>
						<td>${qualityDataIndex.lowerstructureConcreteStrength }</td>
						<td>${qualityDataIndex.lowerstructureConcreteStrengthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.lowerstructureConcreteStrength == 0 ? 0 : (qualityDataIndex.lowerstructureConcreteStrengthPassed 
							/ qualityDataIndex.lowerstructureConcreteStrength) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>钢筋保护层厚度</td>
						<td>${qualityDataIndex.lowerstructureReinforcedProtectiveThickness }</td>
						<td>${qualityDataIndex.lowerstructureReinforcedProtectiveThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.lowerstructureReinforcedProtectiveThickness == 0 ? 0 : (qualityDataIndex.lowerstructureReinforcedProtectiveThicknessPassed 
							/ qualityDataIndex.lowerstructureReinforcedProtectiveThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>墩台垂直度</td>
						<td>${qualityDataIndex.lowerstructurePierVerticality }</td>
						<td>${qualityDataIndex.lowerstructurePierVerticalityPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.lowerstructurePierVerticality == 0 ? 0 : (qualityDataIndex.lowerstructurePierVerticalityPassed 
							/ qualityDataIndex.lowerstructurePierVerticality) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>结构尺寸</td>
						<td>${qualityDataIndex.lowerstructureStructureSize }</td>
						<td>${qualityDataIndex.lowerstructureStructureSizePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.lowerstructureStructureSize == 0 ? 0 : (qualityDataIndex.lowerstructureStructureSizePassed 
							/ qualityDataIndex.lowerstructureStructureSize) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>钢筋间距</td>
						<td>${qualityDataIndex.lowerstructureReinforcedSpacing }</td>
						<td>${qualityDataIndex.lowerstructureReinforcedSpacingPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.lowerstructureReinforcedSpacing == 0 ? 0 : (qualityDataIndex.lowerstructureReinforcedSpacingPassed 
							/ qualityDataIndex.lowerstructureReinforcedSpacing) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>高程及平面位置</td>
						<td>${qualityDataIndex.lowerstructureElevationPlaneLocation }</td>
						<td>${qualityDataIndex.lowerstructureElevationPlaneLocationPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.lowerstructureElevationPlaneLocation == 0 ? 0 : (qualityDataIndex.lowerstructureElevationPlaneLocationPassed 
							/ qualityDataIndex.lowerstructureElevationPlaneLocation) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td rowspan="6">上部结构</td>
						<td>砼强度*</td>
						<td>${qualityDataIndex.superstructureConcreteStrength }</td>
						<td>${qualityDataIndex.superstructureConcreteStrengthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.superstructureConcreteStrength == 0 ? 0 : (qualityDataIndex.superstructureConcreteStrengthPassed 
							/ qualityDataIndex.superstructureConcreteStrength) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>钢筋保护层厚度</td>
						<td>${qualityDataIndex.superstructureReinforcedProtectiveThickness }</td>
						<td>${qualityDataIndex.superstructureReinforcedProtectiveThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.superstructureReinforcedProtectiveThickness == 0 ? 0 : (qualityDataIndex.superstructureReinforcedProtectiveThicknessPassed 
							/ qualityDataIndex.superstructureReinforcedProtectiveThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>结构尺寸</td>
						<td>${qualityDataIndex.superstructureStructureSize }</td>
						<td>${qualityDataIndex.superstructureStructureSizePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.superstructureStructureSize == 0 ? 0 : (qualityDataIndex.superstructureStructureSizePassed 
							/ qualityDataIndex.superstructureStructureSize) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>钢筋间距</td>
						<td>${qualityDataIndex.superstructureReinforcedSpacing }</td>
						<td>${qualityDataIndex.superstructureReinforcedSpacingPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.superstructureReinforcedSpacing == 0 ? 0 : (qualityDataIndex.superstructureReinforcedSpacingPassed 
							/ qualityDataIndex.superstructureReinforcedSpacing) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>高程及平面位置</td>
						<td>${qualityDataIndex.superstructureElevationPlaneLocation }</td>
						<td>${qualityDataIndex.superstructureElevationPlaneLocationPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.superstructureElevationPlaneLocation == 0 ? 0 : (qualityDataIndex.superstructureElevationPlaneLocationPassed 
							/ qualityDataIndex.superstructureElevationPlaneLocation) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>预应力孔道坐标</td>
						<td>${qualityDataIndex.superstructurePrestressedHoleCoordinates }</td>
						<td>${qualityDataIndex.superstructurePrestressedHoleCoordinatesPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.superstructurePrestressedHoleCoordinates == 0 ? 0 : (qualityDataIndex.superstructurePrestressedHoleCoordinatesPassed 
							/ qualityDataIndex.superstructurePrestressedHoleCoordinates) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">桥面宽度*</td>
						<td>${qualityDataIndex.bridgeWidth }</td>
						<td>${qualityDataIndex.bridgeWidthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeWidth == 0 ? 0 : (qualityDataIndex.bridgeWidthPassed 
							/ qualityDataIndex.bridgeWidth) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">桥面厚度*</td>
						<td>${qualityDataIndex.bridgeThickness }</td>
						<td>${qualityDataIndex.bridgeThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeThickness == 0 ? 0 : (qualityDataIndex.bridgeThicknessPassed 
							/ qualityDataIndex.bridgeThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">桥面横坡*</td>
						<td>${qualityDataIndex.bridgeSlope }</td>
						<td>${qualityDataIndex.bridgeSlopePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeSlope == 0 ? 0 : (qualityDataIndex.bridgeSlopePassed 
							/ qualityDataIndex.bridgeSlope) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">硬化混凝土氯离子含量</td>
						<td>${qualityDataIndex.hardenedConcreteChlorideIonContent }</td>
						<td>${qualityDataIndex.hardenedConcreteChlorideIonContentPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.hardenedConcreteChlorideIonContent == 0 ? 0 : (qualityDataIndex.hardenedConcreteChlorideIonContentPassed 
							/ qualityDataIndex.hardenedConcreteChlorideIonContent) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">硬化混凝土碱含量</td>
						<td>${qualityDataIndex.hardenedConcreteAlkaliContent }</td>
						<td>${qualityDataIndex.hardenedConcreteAlkaliContentPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.hardenedConcreteAlkaliContent == 0 ? 0 : (qualityDataIndex.hardenedConcreteAlkaliContentPassed 
							/ qualityDataIndex.hardenedConcreteAlkaliContent) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">混凝土添加剂</td>
						<td>${qualityDataIndex.concreteAdditives }</td>
						<td>${qualityDataIndex.concreteAdditivesPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.concreteAdditives == 0 ? 0 : (qualityDataIndex.concreteAdditivesPassed 
							/ qualityDataIndex.concreteAdditives) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">锚具及张拉预应力</td>
						<td>${qualityDataIndex.anchorsPrestressing }</td>
						<td>${qualityDataIndex.anchorsPrestressingPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.anchorsPrestressing == 0 ? 0 : (qualityDataIndex.anchorsPrestressingPassed 
							/ qualityDataIndex.anchorsPrestressing) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">板式橡胶支座及安装质量</td>
						<td>${qualityDataIndex.plateRubberBearingInstallationQuality }</td>
						<td>${qualityDataIndex.plateRubberBearingInstallationQualityPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.plateRubberBearingInstallationQuality == 0 ? 0 : (qualityDataIndex.plateRubberBearingInstallationQualityPassed 
							/ qualityDataIndex.plateRubberBearingInstallationQuality) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">桥面系顶面标高</td>
						<td>${qualityDataIndex.deckTopSurfaceElevation }</td>
						<td>${qualityDataIndex.deckTopSurfaceElevationPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.deckTopSurfaceElevation == 0 ? 0 : (qualityDataIndex.deckTopSurfaceElevationPassed 
							/ qualityDataIndex.deckTopSurfaceElevation) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">裂缝宽度</td>
						<td>${qualityDataIndex.crackWidth }</td>
						<td>${qualityDataIndex.crackWidthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.crackWidth == 0 ? 0 : (qualityDataIndex.crackWidthPassed 
							/ qualityDataIndex.crackWidth) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">桩基</td>
						<td>${qualityDataIndex.pileFoundation }</td>
						<td>${qualityDataIndex.pileFoundationPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.pileFoundation == 0 ? 0 : (qualityDataIndex.pileFoundationPassed 
							/ qualityDataIndex.pileFoundation) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">其它</td>
						<td>${qualityDataIndex.bridgeEngineeringOthers }</td>
						<td>${qualityDataIndex.bridgeEngineeringOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.bridgeEngineeringOthers == 0 ? 0 : (qualityDataIndex.bridgeEngineeringOthersPassed 
							/ qualityDataIndex.bridgeEngineeringOthers) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">小计</td>
						<td>${qualityDataIndex.lowerstructureConcreteStrength
									+ qualityDataIndex.lowerstructurePierVerticality
									+ qualityDataIndex.lowerstructureStructureSize
									+ qualityDataIndex.lowerstructureReinforcedSpacing
									+ qualityDataIndex.lowerstructureElevationPlaneLocation
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureConcreteStrength
									+ qualityDataIndex.superstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureStructureSize
									+ qualityDataIndex.superstructureReinforcedSpacing
									+ qualityDataIndex.superstructureElevationPlaneLocation
									+ qualityDataIndex.superstructurePrestressedHoleCoordinates
									+ qualityDataIndex.bridgeWidth
									+ qualityDataIndex.bridgeThickness
									+ qualityDataIndex.bridgeSlope
									+ qualityDataIndex.hardenedConcreteChlorideIonContent
									+ qualityDataIndex.hardenedConcreteAlkaliContent
									+ qualityDataIndex.concreteAdditives
									+ qualityDataIndex.anchorsPrestressing
									+ qualityDataIndex.plateRubberBearingInstallationQuality
									+ qualityDataIndex.deckTopSurfaceElevation
									+ qualityDataIndex.crackWidth
									+ qualityDataIndex.pileFoundation
									+ qualityDataIndex.bridgeEngineeringOthers }</td>
						<td>${qualityDataIndex.anchorsPrestressingPassed
									+ qualityDataIndex.bridgeEngineeringOthersPassed
									+ qualityDataIndex.bridgeSlopePassed
									+ qualityDataIndex.bridgeThicknessPassed
									+ qualityDataIndex.bridgeWidthPassed
									+ qualityDataIndex.concreteAdditivesPassed
									+ qualityDataIndex.crackWidthPassed
									+ qualityDataIndex.deckTopSurfaceElevationPassed
									+ qualityDataIndex.hardenedConcreteAlkaliContentPassed
									+ qualityDataIndex.hardenedConcreteChlorideIonContentPassed
									+ qualityDataIndex.lowerstructureConcreteStrengthPassed
									+ qualityDataIndex.lowerstructureElevationPlaneLocationPassed
									+ qualityDataIndex.lowerstructurePierVerticalityPassed
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThicknessPassed
									+ qualityDataIndex.lowerstructureReinforcedSpacingPassed
									+ qualityDataIndex.lowerstructureStructureSizePassed
									+ qualityDataIndex.pileFoundationPassed
									+ qualityDataIndex.plateRubberBearingInstallationQualityPassed
									+ qualityDataIndex.superstructureConcreteStrengthPassed
									+ qualityDataIndex.superstructureElevationPlaneLocationPassed
									+ qualityDataIndex.superstructurePrestressedHoleCoordinatesPassed
									+ qualityDataIndex.superstructureReinforcedProtectiveThicknessPassed
									+ qualityDataIndex.superstructureReinforcedSpacingPassed
									+ qualityDataIndex.superstructureStructureSizePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${(
									qualityDataIndex.lowerstructureConcreteStrength
									+ qualityDataIndex.lowerstructurePierVerticality
									+ qualityDataIndex.lowerstructureStructureSize
									+ qualityDataIndex.lowerstructureReinforcedSpacing
									+ qualityDataIndex.lowerstructureElevationPlaneLocation
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureConcreteStrength
									+ qualityDataIndex.superstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureStructureSize
									+ qualityDataIndex.superstructureReinforcedSpacing
									+ qualityDataIndex.superstructureElevationPlaneLocation
									+ qualityDataIndex.superstructurePrestressedHoleCoordinates
									+ qualityDataIndex.bridgeWidth
									+ qualityDataIndex.bridgeThickness
									+ qualityDataIndex.bridgeSlope
									+ qualityDataIndex.hardenedConcreteChlorideIonContent
									+ qualityDataIndex.hardenedConcreteAlkaliContent
									+ qualityDataIndex.concreteAdditives
									+ qualityDataIndex.anchorsPrestressing
									+ qualityDataIndex.plateRubberBearingInstallationQuality
									+ qualityDataIndex.deckTopSurfaceElevation
									+ qualityDataIndex.crackWidth
									+ qualityDataIndex.pileFoundation
									+ qualityDataIndex.bridgeEngineeringOthers) == 0 ? 0 : ((
									qualityDataIndex.anchorsPrestressingPassed
									+ qualityDataIndex.bridgeEngineeringOthersPassed
									+ qualityDataIndex.bridgeSlopePassed
									+ qualityDataIndex.bridgeThicknessPassed
									+ qualityDataIndex.bridgeWidthPassed
									+ qualityDataIndex.concreteAdditivesPassed
									+ qualityDataIndex.crackWidthPassed
									+ qualityDataIndex.deckTopSurfaceElevationPassed
									+ qualityDataIndex.hardenedConcreteAlkaliContentPassed
									+ qualityDataIndex.hardenedConcreteChlorideIonContentPassed
									+ qualityDataIndex.lowerstructureConcreteStrengthPassed
									+ qualityDataIndex.lowerstructureElevationPlaneLocationPassed
									+ qualityDataIndex.lowerstructurePierVerticalityPassed
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThicknessPassed
									+ qualityDataIndex.lowerstructureReinforcedSpacingPassed
									+ qualityDataIndex.lowerstructureStructureSizePassed
									+ qualityDataIndex.pileFoundationPassed
									+ qualityDataIndex.plateRubberBearingInstallationQualityPassed
									+ qualityDataIndex.superstructureConcreteStrengthPassed
									+ qualityDataIndex.superstructureElevationPlaneLocationPassed
									+ qualityDataIndex.superstructurePrestressedHoleCoordinatesPassed
									+ qualityDataIndex.superstructureReinforcedProtectiveThicknessPassed
									+ qualityDataIndex.superstructureReinforcedSpacingPassed
									+ qualityDataIndex.superstructureStructureSizePassed )/(
									qualityDataIndex.lowerstructureConcreteStrength
									+ qualityDataIndex.lowerstructurePierVerticality
									+ qualityDataIndex.lowerstructureStructureSize
									+ qualityDataIndex.lowerstructureReinforcedSpacing
									+ qualityDataIndex.lowerstructureElevationPlaneLocation
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureConcreteStrength
									+ qualityDataIndex.superstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureStructureSize
									+ qualityDataIndex.superstructureReinforcedSpacing
									+ qualityDataIndex.superstructureElevationPlaneLocation
									+ qualityDataIndex.superstructurePrestressedHoleCoordinates
									+ qualityDataIndex.bridgeWidth
									+ qualityDataIndex.bridgeThickness
									+ qualityDataIndex.bridgeSlope
									+ qualityDataIndex.hardenedConcreteChlorideIonContent
									+ qualityDataIndex.hardenedConcreteAlkaliContent
									+ qualityDataIndex.concreteAdditives
									+ qualityDataIndex.anchorsPrestressing
									+ qualityDataIndex.plateRubberBearingInstallationQuality
									+ qualityDataIndex.deckTopSurfaceElevation
									+ qualityDataIndex.crackWidth
									+ qualityDataIndex.pileFoundation
									+ qualityDataIndex.bridgeEngineeringOthers)) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>

					<!-- 隧道工程start -->
					<tr>
						<td rowspan="15">隧道工程</td>
						<td rowspan="4">衬砌支护</td>
						<td>砼强度*</td>
						<td>${qualityDataIndex.liningSupportConcreteStrength }</td>
						<td>${qualityDataIndex.liningSupportConcreteStrengthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.liningSupportConcreteStrength == 0 ? 0 : (qualityDataIndex.liningSupportConcreteStrengthPassed 
							/ qualityDataIndex.liningSupportConcreteStrength) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>衬砌厚度*</td>
						<td>${qualityDataIndex.liningSupportLiningThickness }</td>
						<td>${qualityDataIndex.liningSupportLiningThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.liningSupportLiningThickness == 0 ? 0 : (qualityDataIndex.liningSupportLiningThicknessPassed 
							/ qualityDataIndex.liningSupportLiningThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>平整度</td>
						<td>${qualityDataIndex.liningSupportFlatness }</td>
						<td>${qualityDataIndex.liningSupportFlatnessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.liningSupportFlatness == 0 ? 0 : (qualityDataIndex.liningSupportFlatnessPassed 
							/ qualityDataIndex.liningSupportFlatness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td>锚杆拉拔</td>
						<td>${qualityDataIndex.liningSupportAnchorDrawing }</td>
						<td>${qualityDataIndex.liningSupportAnchorDrawingPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.liningSupportAnchorDrawing == 0 ? 0 : (qualityDataIndex.liningSupportAnchorDrawingPassed 
							/ qualityDataIndex.liningSupportAnchorDrawing) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">锚杆间距、长度及注浆密实度*</td>
						<td>${qualityDataIndex.anchorSpacingLengthGrouting }</td>
						<td>${qualityDataIndex.anchorSpacingLengthGroutingPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.anchorSpacingLengthGrouting == 0 ? 0 : (qualityDataIndex.anchorSpacingLengthGroutingPassed 
							/ qualityDataIndex.anchorSpacingLengthGrouting) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">净空*</td>
						<td>${qualityDataIndex.clearance }</td>
						<td>${qualityDataIndex.clearancePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.clearance == 0 ? 0 : (qualityDataIndex.clearancePassed 
							/ qualityDataIndex.clearance) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">断面轮廓</td>
						<td>${qualityDataIndex.sectionOutline }</td>
						<td>${qualityDataIndex.sectionOutlinePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.sectionOutline == 0 ? 0 : (qualityDataIndex.sectionOutlinePassed 
							/ qualityDataIndex.sectionOutline) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">拱架间距</td>
						<td>${qualityDataIndex.archesSpacing }</td>
						<td>${qualityDataIndex.archesSpacingPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.archesSpacing == 0 ? 0 : (qualityDataIndex.archesSpacingPassed 
							/ qualityDataIndex.archesSpacing) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">钢筋间距</td>
						<td>${qualityDataIndex.reinforcedSpacing }</td>
						<td>${qualityDataIndex.reinforcedSpacingPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.reinforcedSpacing == 0 ? 0 : (qualityDataIndex.reinforcedSpacingPassed 
							/ qualityDataIndex.reinforcedSpacing) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">空洞</td>
						<td>${qualityDataIndex.empties }</td>
						<td>${qualityDataIndex.emptiesPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.empties == 0 ? 0 : (qualityDataIndex.emptiesPassed 
							/ qualityDataIndex.empties) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">防水板质量搭接宽度</td>
						<td>${qualityDataIndex.waterproofBoardWeldingWide }</td>
						<td>${qualityDataIndex.waterproofBoardWeldingWidePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.waterproofBoardWeldingWide == 0 ? 0 : (qualityDataIndex.waterproofBoardWeldingWidePassed 
							/ qualityDataIndex.waterproofBoardWeldingWide) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">超前小导管数量或间距</td>
						<td>${qualityDataIndex.leadSmallNumIndirect }</td>
						<td>${qualityDataIndex.leadSmallNumIndirectPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.leadSmallNumIndirect == 0 ? 0 : (qualityDataIndex.leadSmallNumIndirectPassed 
							/ qualityDataIndex.leadSmallNumIndirect) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">隧道路面</td>
						<td>${qualityDataIndex.tunnelPavement }</td>
						<td>${qualityDataIndex.tunnelPavementPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.tunnelPavement == 0 ? 0 : (qualityDataIndex.tunnelPavementPassed 
							/ qualityDataIndex.tunnelPavement) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">其他</td>
						<td>${qualityDataIndex.tunnelEngineeringOthers }</td>
						<td>${qualityDataIndex.tunnelEngineeringOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.tunnelEngineeringOthers == 0 ? 0 : (qualityDataIndex.tunnelEngineeringOthersPassed 
							/ qualityDataIndex.tunnelEngineeringOthers) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">小计</td>
						<td>${qualityDataIndex.liningSupportConcreteStrength					
									+ qualityDataIndex.liningSupportLiningThickness							
									+ qualityDataIndex.liningSupportFlatness										
									+ qualityDataIndex.liningSupportAnchorDrawing								
									+ qualityDataIndex.anchorSpacingLengthGrouting				
									+ qualityDataIndex.clearance													
									+ qualityDataIndex.sectionOutline											
									+ qualityDataIndex.archesSpacing													
									+ qualityDataIndex.reinforcedSpacing											
									+ qualityDataIndex.empties																
									+ qualityDataIndex.waterproofBoardWeldingWide							
									+ qualityDataIndex.leadSmallNumIndirect										
									+ qualityDataIndex.tunnelPavement													
									+ qualityDataIndex.tunnelEngineeringOthers	 }</td>
						<td>${qualityDataIndex.liningSupportConcreteStrengthPassed	
									+ qualityDataIndex.liningSupportLiningThicknessPassed
									+ qualityDataIndex.liningSupportFlatnessPassed
									+ qualityDataIndex.anchorSpacingLengthGroutingPassed
									+ qualityDataIndex.liningSupportAnchorDrawingPassed
									+ qualityDataIndex.clearancePassed
									+ qualityDataIndex.sectionOutlinePassed
									+ qualityDataIndex.archesSpacingPassed
									+ qualityDataIndex.reinforcedSpacingPassed
									+ qualityDataIndex.emptiesPassed
									+ qualityDataIndex.waterproofBoardWeldingWidePassed
									+ qualityDataIndex.leadSmallNumIndirectPassed
									+ qualityDataIndex.tunnelPavementPassed
									+ qualityDataIndex.tunnelEngineeringOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${(
									qualityDataIndex.liningSupportConcreteStrength					
										+ qualityDataIndex.liningSupportLiningThickness							
										+ qualityDataIndex.liningSupportFlatness										
										+ qualityDataIndex.liningSupportAnchorDrawing								
										+ qualityDataIndex.anchorSpacingLengthGrouting				
										+ qualityDataIndex.clearance													
										+ qualityDataIndex.sectionOutline											
										+ qualityDataIndex.archesSpacing													
										+ qualityDataIndex.reinforcedSpacing											
										+ qualityDataIndex.empties																
										+ qualityDataIndex.waterproofBoardWeldingWide							
										+ qualityDataIndex.leadSmallNumIndirect										
										+ qualityDataIndex.tunnelPavement													
										+ qualityDataIndex.tunnelEngineeringOthers	) == 0 ? 0 : ((
									qualityDataIndex.liningSupportConcreteStrengthPassed	
										+ qualityDataIndex.liningSupportLiningThicknessPassed
										+ qualityDataIndex.liningSupportFlatnessPassed
										+ qualityDataIndex.anchorSpacingLengthGroutingPassed
										+ qualityDataIndex.liningSupportAnchorDrawingPassed
										+ qualityDataIndex.clearancePassed
										+ qualityDataIndex.sectionOutlinePassed
										+ qualityDataIndex.archesSpacingPassed
										+ qualityDataIndex.reinforcedSpacingPassed
										+ qualityDataIndex.emptiesPassed
										+ qualityDataIndex.waterproofBoardWeldingWidePassed
										+ qualityDataIndex.leadSmallNumIndirectPassed
										+ qualityDataIndex.tunnelPavementPassed
										+ qualityDataIndex.tunnelEngineeringOthersPassed )/(
									qualityDataIndex.liningSupportConcreteStrength					
										+ qualityDataIndex.liningSupportLiningThickness							
										+ qualityDataIndex.liningSupportFlatness										
										+ qualityDataIndex.liningSupportAnchorDrawing								
										+ qualityDataIndex.anchorSpacingLengthGrouting				
										+ qualityDataIndex.clearance													
										+ qualityDataIndex.sectionOutline											
										+ qualityDataIndex.archesSpacing													
										+ qualityDataIndex.reinforcedSpacing											
										+ qualityDataIndex.empties																
										+ qualityDataIndex.waterproofBoardWeldingWide							
										+ qualityDataIndex.leadSmallNumIndirect										
										+ qualityDataIndex.tunnelPavement													
										+ qualityDataIndex.tunnelEngineeringOthers	)) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>

					<!-- 原材料start -->
					<tr>
						<td rowspan="16">原材料</td>
						<td colspan="2">钢材*</td>
						<td>${qualityDataIndex.reinforced }</td>
						<td>${qualityDataIndex.reinforcedPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.reinforced == 0 ? 0 : (qualityDataIndex.reinforcedPassed 
							/ qualityDataIndex.reinforced) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">水泥*</td>
						<td>${qualityDataIndex.cement }</td>
						<td>${qualityDataIndex.cementPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.cement == 0 ? 0 : (qualityDataIndex.cementPassed 
							/ qualityDataIndex.cement) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">沥青*</td>
						<td>${qualityDataIndex.asphalt }</td>
						<td>${qualityDataIndex.asphaltPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.asphalt == 0 ? 0 : (qualityDataIndex.asphaltPassed 
							/ qualityDataIndex.asphalt) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">石灰*</td>
						<td>${qualityDataIndex.lime }</td>
						<td>${qualityDataIndex.limePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.lime == 0 ? 0 : (qualityDataIndex.limePassed 
							/ qualityDataIndex.lime) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">碎石*</td>
						<td>${qualityDataIndex.gravel }</td>
						<td>${qualityDataIndex.gravelsandPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.gravel == 0 ? 0 : (qualityDataIndex.gravelsandPassed 
							/ qualityDataIndex.gravel) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">砂*</td>
						<td>${qualityDataIndex.sand }</td>
						<td>${qualityDataIndex.sandPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.sand == 0 ? 0 : (qualityDataIndex.sandPassed 
							/ qualityDataIndex.sand) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">矿粉*</td>
						<td>${qualityDataIndex.mineralPowder }</td>
						<td>${qualityDataIndex.mineralPowderPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.mineralPowder == 0 ? 0 : (qualityDataIndex.mineralPowderPassed 
							/ qualityDataIndex.mineralPowder) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">橡胶支座*</td>
						<td>${qualityDataIndex.rubberBearings }</td>
						<td>${qualityDataIndex.rubberBearingsPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.rubberBearings == 0 ? 0 : (qualityDataIndex.rubberBearingsPassed 
							/ qualityDataIndex.rubberBearings) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">锚具*</td>
						<td>${qualityDataIndex.anchor }</td>
						<td>${qualityDataIndex.anchorPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.anchor == 0 ? 0 : (qualityDataIndex.anchorPassed 
							/ qualityDataIndex.anchor) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">拼接螺栓*</td>
						<td>${qualityDataIndex.splicingBolts }</td>
						<td>${qualityDataIndex.splicingBoltsPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.splicingBolts == 0 ? 0 : (qualityDataIndex.splicingBoltsPassed 
							/ qualityDataIndex.splicingBolts) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">土工布*</td>
						<td>${qualityDataIndex.geotextile }</td>
						<td>${qualityDataIndex.geotextilePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.geotextile == 0 ? 0 : (qualityDataIndex.geotextilePassed 
							/ qualityDataIndex.geotextile) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">防水板*</td>
						<td>${qualityDataIndex.waterproofBoard }</td>
						<td>${qualityDataIndex.waterproofBoardPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.waterproofBoard == 0 ? 0 : (qualityDataIndex.waterproofBoardPassed 
							/ qualityDataIndex.waterproofBoard) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">排水管*</td>
						<td>${qualityDataIndex.drain }</td>
						<td>${qualityDataIndex.drainPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.drain == 0 ? 0 : (qualityDataIndex.drainPassed 
							/ qualityDataIndex.drain) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">压浆料*</td>
						<td>${qualityDataIndex.slurry }</td>
						<td>${qualityDataIndex.slurryPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.slurry == 0 ? 0 : (qualityDataIndex.slurryPassed 
							/ qualityDataIndex.slurry) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">其他*</td>
						<td>${qualityDataIndex.materialsOthers }</td>
						<td>${qualityDataIndex.materialsOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.materialsOthers == 0 ? 0 : (qualityDataIndex.materialsOthersPassed 
							/ qualityDataIndex.materialsOthers) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">小计</td>
						<td>${qualityDataIndex.reinforced
									+ qualityDataIndex.cement
									+ qualityDataIndex.asphalt
									+ qualityDataIndex.lime
									+ qualityDataIndex.gravel
									+ qualityDataIndex.sand
									+ qualityDataIndex.mineralPowder
									+ qualityDataIndex.rubberBearings
									+ qualityDataIndex.anchor
									+ qualityDataIndex.splicingBolts
									+ qualityDataIndex.geotextile
									+ qualityDataIndex.waterproofBoard
									+ qualityDataIndex.drain
									+ qualityDataIndex.slurry
									+ qualityDataIndex.materialsOthers	 }</td>
						<td>${qualityDataIndex.reinforcedPassed
									+ qualityDataIndex.cementPassed
									+ qualityDataIndex.asphaltPassed
									+ qualityDataIndex.limePassed
									+ qualityDataIndex.gravelsandPassed
									+ qualityDataIndex.sandPassed
									+ qualityDataIndex.mineralPowderPassed
									+ qualityDataIndex.rubberBearingsPassed
									+ qualityDataIndex.anchorPassed
									+ qualityDataIndex.splicingBoltsPassed
									+ qualityDataIndex.geotextilePassed
									+ qualityDataIndex.waterproofBoardPassed
									+ qualityDataIndex.drainPassed
									+ qualityDataIndex.slurryPassed
									+ qualityDataIndex.materialsOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${(
									qualityDataIndex.reinforced
										+ qualityDataIndex.cement
										+ qualityDataIndex.asphalt
										+ qualityDataIndex.lime
										+ qualityDataIndex.gravel
										+ qualityDataIndex.sand
										+ qualityDataIndex.mineralPowder
										+ qualityDataIndex.rubberBearings
										+ qualityDataIndex.anchor
										+ qualityDataIndex.splicingBolts
										+ qualityDataIndex.geotextile
										+ qualityDataIndex.waterproofBoard
										+ qualityDataIndex.drain
										+ qualityDataIndex.slurry
										+ qualityDataIndex.materialsOthers	) == 0 ? 0 : ((
									qualityDataIndex.reinforcedPassed
										+ qualityDataIndex.cementPassed
										+ qualityDataIndex.asphaltPassed
										+ qualityDataIndex.limePassed
										+ qualityDataIndex.gravelsandPassed
										+ qualityDataIndex.sandPassed
										+ qualityDataIndex.mineralPowderPassed
										+ qualityDataIndex.rubberBearingsPassed
										+ qualityDataIndex.anchorPassed
										+ qualityDataIndex.splicingBoltsPassed
										+ qualityDataIndex.geotextilePassed
										+ qualityDataIndex.waterproofBoardPassed
										+ qualityDataIndex.drainPassed
										+ qualityDataIndex.slurryPassed
										+ qualityDataIndex.materialsOthersPassed )/(
									qualityDataIndex.reinforced
										+ qualityDataIndex.cement
										+ qualityDataIndex.asphalt
										+ qualityDataIndex.lime
										+ qualityDataIndex.gravel
										+ qualityDataIndex.sand
										+ qualityDataIndex.mineralPowder
										+ qualityDataIndex.rubberBearings
										+ qualityDataIndex.anchor
										+ qualityDataIndex.splicingBolts
										+ qualityDataIndex.geotextile
										+ qualityDataIndex.waterproofBoard
										+ qualityDataIndex.drain
										+ qualityDataIndex.slurry
										+ qualityDataIndex.materialsOthers	)) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>

					<!-- 交通安全设施start -->
					<tr>
						<td rowspan="13">交通安全设施</td>
						<td colspan="2">构件基底厚度</td>
						<td>${qualityDataIndex.componentBaseThickness }</td>
						<td>${qualityDataIndex.componentBaseThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.componentBaseThickness == 0 ? 0 : (qualityDataIndex.componentBaseThicknessPassed 
							/ qualityDataIndex.componentBaseThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">构件防腐层厚度</td>
						<td>${qualityDataIndex.componentCoatingThickness }</td>
						<td>${qualityDataIndex.componentCoatingThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.componentCoatingThickness == 0 ? 0 : (qualityDataIndex.componentCoatingThicknessPassed 
							/ qualityDataIndex.componentCoatingThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">护栏横梁中心高度*</td>
						<td>${qualityDataIndex.fencebeamCenterHeight }</td>
						<td>${qualityDataIndex.fencebeamCenterHeightPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.fencebeamCenterHeight == 0 ? 0 : (qualityDataIndex.fencebeamCenterHeightPassed 
							/ qualityDataIndex.fencebeamCenterHeight) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">护栏立柱埋入深度</td>
						<td>${qualityDataIndex.fenceColumnEmbeddedDepth }</td>
						<td>${qualityDataIndex.fenceColumnEmbeddedDepthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.fenceColumnEmbeddedDepth == 0 ? 0 : (qualityDataIndex.fenceColumnEmbeddedDepthPassed 
							/ qualityDataIndex.fenceColumnEmbeddedDepth) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">拼接螺栓抗拉强度</td>
						<td>${qualityDataIndex.stitchingBoltTensileStrength }</td>
						<td>${qualityDataIndex.stitchingBoltTensileStrengthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.stitchingBoltTensileStrength == 0 ? 0 : (qualityDataIndex.stitchingBoltTensileStrengthPassed 
							/ qualityDataIndex.stitchingBoltTensileStrength) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">立柱壁厚度*</td>
						<td>${qualityDataIndex.pillarWallThickness }</td>
						<td>${qualityDataIndex.pillarWallThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.pillarWallThickness == 0 ? 0 : (qualityDataIndex.pillarWallThicknessPassed 
							/ qualityDataIndex.pillarWallThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">混凝土护栏强度*</td>
						<td>${qualityDataIndex.concreteFenceStrength }</td>
						<td>${qualityDataIndex.concreteFenceStrengthPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.concreteFenceStrength == 0 ? 0 : (qualityDataIndex.concreteFenceStrengthPassed 
							/ qualityDataIndex.concreteFenceStrength) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">波形板厚度*</td>
						<td>${qualityDataIndex.wavePlateThickness }</td>
						<td>${qualityDataIndex.wavePlateThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.wavePlateThickness == 0 ? 0 : (qualityDataIndex.wavePlateThicknessPassed 
							/ qualityDataIndex.wavePlateThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">标志板垂直度与净空</td>
						<td>${qualityDataIndex.signBoardClearance }</td>
						<td>${qualityDataIndex.signBoardClearancePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.signBoardClearance == 0 ? 0 : (qualityDataIndex.signBoardClearancePassed 
							/ qualityDataIndex.signBoardClearance) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">标线厚度</td>
						<td>${qualityDataIndex.signThickness }</td>
						<td>${qualityDataIndex.signThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.signThickness == 0 ? 0 : (qualityDataIndex.signThicknessPassed 
							/ qualityDataIndex.signThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">标线逆反射系数</td>
						<td>${qualityDataIndex.signReverseReflection }</td>
						<td>${qualityDataIndex.signReverseReflectionPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.signReverseReflection == 0 ? 0 : (qualityDataIndex.signReverseReflectionPassed 
							/ qualityDataIndex.signReverseReflection) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">其他</td>
						<td>${qualityDataIndex.trafficOthers }</td>
						<td>${qualityDataIndex.trafficOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.trafficOthers == 0 ? 0 : (qualityDataIndex.trafficOthersPassed 
							/ qualityDataIndex.trafficOthers) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">小计</td>
						<td>${qualityDataIndex.componentBaseThickness
										+ qualityDataIndex.componentCoatingThickness
										+ qualityDataIndex.fencebeamCenterHeight
										+ qualityDataIndex.fenceColumnEmbeddedDepth
										+ qualityDataIndex.stitchingBoltTensileStrength
										+ qualityDataIndex.pillarWallThickness
										+ qualityDataIndex.concreteFenceStrength
										+ qualityDataIndex.wavePlateThickness
										+ qualityDataIndex.signBoardClearance
										+ qualityDataIndex.signThickness
										+ qualityDataIndex.signReverseReflection
										+ qualityDataIndex.trafficOthers	 }</td>
						<td>${qualityDataIndex.componentBaseThicknessPassed
										+ qualityDataIndex.componentCoatingThicknessPassed
										+ qualityDataIndex.fencebeamCenterHeightPassed
										+ qualityDataIndex.fenceColumnEmbeddedDepthPassed
										+ qualityDataIndex.stitchingBoltTensileStrengthPassed
										+ qualityDataIndex.pillarWallThicknessPassed
										+ qualityDataIndex.concreteFenceStrengthPassed
										+ qualityDataIndex.wavePlateThicknessPassed
										+ qualityDataIndex.signBoardClearancePassed
										+ qualityDataIndex.signThicknessPassed
										+ qualityDataIndex.signReverseReflectionPassed
										+ qualityDataIndex.trafficOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${(
									qualityDataIndex.componentBaseThickness
										+ qualityDataIndex.componentCoatingThickness
										+ qualityDataIndex.fencebeamCenterHeight
										+ qualityDataIndex.fenceColumnEmbeddedDepth
										+ qualityDataIndex.stitchingBoltTensileStrength
										+ qualityDataIndex.pillarWallThickness
										+ qualityDataIndex.concreteFenceStrength
										+ qualityDataIndex.wavePlateThickness
										+ qualityDataIndex.signBoardClearance
										+ qualityDataIndex.signThickness
										+ qualityDataIndex.signReverseReflection
										+ qualityDataIndex.trafficOthers	) == 0 ? 0: ( (
									qualityDataIndex.componentBaseThicknessPassed
										+ qualityDataIndex.componentCoatingThicknessPassed
										+ qualityDataIndex.fencebeamCenterHeightPassed
										+ qualityDataIndex.fenceColumnEmbeddedDepthPassed
										+ qualityDataIndex.stitchingBoltTensileStrengthPassed
										+ qualityDataIndex.pillarWallThicknessPassed
										+ qualityDataIndex.concreteFenceStrengthPassed
										+ qualityDataIndex.wavePlateThicknessPassed
										+ qualityDataIndex.signBoardClearancePassed
										+ qualityDataIndex.signThicknessPassed
										+ qualityDataIndex.signReverseReflectionPassed
										+ qualityDataIndex.trafficOthersPassed )/(
									qualityDataIndex.componentBaseThickness
										+ qualityDataIndex.componentCoatingThickness
										+ qualityDataIndex.fencebeamCenterHeight
										+ qualityDataIndex.fenceColumnEmbeddedDepth
										+ qualityDataIndex.stitchingBoltTensileStrength
										+ qualityDataIndex.pillarWallThickness
										+ qualityDataIndex.concreteFenceStrength
										+ qualityDataIndex.wavePlateThickness
										+ qualityDataIndex.signBoardClearance
										+ qualityDataIndex.signThickness
										+ qualityDataIndex.signReverseReflection
										+ qualityDataIndex.trafficOthers	)) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>

					<!-- 机电工程start -->
					<tr>
						<td rowspan="8">机电</td>
						<td colspan="2">网线质量</td>
						<td>${qualityDataIndex.networkQuality }</td>
						<td>${qualityDataIndex.networkQualityPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.networkQuality == 0 ? 0 : (qualityDataIndex.networkQualityPassed 
							/ qualityDataIndex.networkQuality) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">涂层厚度</td>
						<td>${qualityDataIndex.coatingThickness }</td>
						<td>${qualityDataIndex.coatingThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.coatingThickness == 0 ? 0 : (qualityDataIndex.coatingThicknessPassed 
							/ qualityDataIndex.coatingThickness) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">绝缘电阻</td>
						<td>${qualityDataIndex.insulationResistance }</td>
						<td>${qualityDataIndex.insulationResistancePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.insulationResistance == 0 ? 0 : (qualityDataIndex.insulationResistancePassed 
							/ qualityDataIndex.insulationResistance) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">接地电阻</td>
						<td>${qualityDataIndex.groundingResistance }</td>
						<td>${qualityDataIndex.groundingResistancePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.groundingResistance == 0 ? 0 : (qualityDataIndex.groundingResistancePassed 
							/ qualityDataIndex.groundingResistance) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">灯具功率因数</td>
						<td>${qualityDataIndex.powerFactors }</td>
						<td>${qualityDataIndex.powerFactorsPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.powerFactors == 0 ? 0 : (qualityDataIndex.powerFactorsPassed 
							/ qualityDataIndex.powerFactors) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">风机安装净空</td>
						<td>${qualityDataIndex.blowerClearance }</td>
						<td>${qualityDataIndex.blowerClearancePassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.blowerClearance == 0 ? 0 : (qualityDataIndex.blowerClearancePassed 
							/ qualityDataIndex.blowerClearance) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">其他</td>
						<td>${qualityDataIndex.meOthers }</td>
						<td>${qualityDataIndex.meOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${qualityDataIndex.meOthers == 0 ? 0 : (qualityDataIndex.meOthersPassed 
							/ qualityDataIndex.meOthers) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="2">小计</td>
						<td>${qualityDataIndex.networkQuality						
										+ qualityDataIndex.coatingThickness					
										+ qualityDataIndex.insulationResistance			
										+ qualityDataIndex.groundingResistance			
										+ qualityDataIndex.powerFactors							
										+ qualityDataIndex.blowerClearance			
										+ qualityDataIndex.meOthers	 }</td>
						<td>${qualityDataIndex.networkQualityPassed
										+ qualityDataIndex.coatingThicknessPassed
										+ qualityDataIndex.insulationResistancePassed
										+ qualityDataIndex.groundingResistancePassed
										+ qualityDataIndex.powerFactorsPassed
										+ qualityDataIndex.blowerClearancePassed
										+ qualityDataIndex.meOthersPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${(
									qualityDataIndex.networkQuality						
										+ qualityDataIndex.coatingThickness					
										+ qualityDataIndex.insulationResistance			
										+ qualityDataIndex.groundingResistance			
										+ qualityDataIndex.powerFactors							
										+ qualityDataIndex.blowerClearance			
										+ qualityDataIndex.meOthers	) == 0 ? 0: ( (
									qualityDataIndex.networkQualityPassed
										+ qualityDataIndex.coatingThicknessPassed
										+ qualityDataIndex.insulationResistancePassed
										+ qualityDataIndex.groundingResistancePassed
										+ qualityDataIndex.powerFactorsPassed
										+ qualityDataIndex.blowerClearancePassed
										+ qualityDataIndex.meOthersPassed )/(
									qualityDataIndex.networkQuality						
										+ qualityDataIndex.coatingThickness					
										+ qualityDataIndex.insulationResistance			
										+ qualityDataIndex.groundingResistance			
										+ qualityDataIndex.powerFactors							
										+ qualityDataIndex.blowerClearance			
										+ qualityDataIndex.meOthers	) )* 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="3">总计</td>
						<td>${(qualityDataIndex.earthworkCompaction					
									+ qualityDataIndex.earthworkDeflection					
									+ qualityDataIndex.earthworkAshDosage						
									+ qualityDataIndex.drainageSectionSize					
									+ qualityDataIndex.drainagePavedThickness					
									+ qualityDataIndex.bridgeConcreteStrength					
									+ qualityDataIndex.bridgeReinforcedThickness				
									+ qualityDataIndex.bridgeStructureSize					
									+ qualityDataIndex.bridgeCulvertSpacing					
									+ qualityDataIndex.bridgeCulvertElevationPosition			
									+ qualityDataIndex.supportConcreteStrength				
									+ qualityDataIndex.supportSectionSize						
									+ qualityDataIndex.subgradeEngineeringOthers)+ (qualityDataIndex.surfaceCompaction
									+ qualityDataIndex.surfaceConcreteRoadStrength
									+ qualityDataIndex.surfaceDeflection
									+ qualityDataIndex.surfaceAsphaltContent
									+ qualityDataIndex.surfacePorosity
									+ qualityDataIndex.surfaceThickness
									+ qualityDataIndex.surfaceFlatness
									+ qualityDataIndex.surfaceCrossSlope
									+ qualityDataIndex.surfaceElevation
									+ qualityDataIndex.surfaceWaterSeepageCoefficient
									+ qualityDataIndex.surfaceSlabHeightDifference
									+ qualityDataIndex.surfaceAntiSlipSurface
									+ qualityDataIndex.grassrootsLevelCompaction
									+ qualityDataIndex.grassrootsSubbaseGrayAmount
									+ qualityDataIndex.grassrootsThickness
									+ qualityDataIndex.grassrootsUnitIntegrity
									+ qualityDataIndex.grassrootsIntensity
									+ qualityDataIndex.grassrootsLevelCracks
									+ qualityDataIndex.grassrootsCrossSlope
									+ qualityDataIndex.grassrootsElevation
									+ qualityDataIndex.pavementEngineeringOthers) + (qualityDataIndex.lowerstructureConcreteStrength
									+ qualityDataIndex.lowerstructurePierVerticality
									+ qualityDataIndex.lowerstructureStructureSize
									+ qualityDataIndex.lowerstructureReinforcedSpacing
									+ qualityDataIndex.lowerstructureElevationPlaneLocation
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureConcreteStrength
									+ qualityDataIndex.superstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureStructureSize
									+ qualityDataIndex.superstructureReinforcedSpacing
									+ qualityDataIndex.superstructureElevationPlaneLocation
									+ qualityDataIndex.superstructurePrestressedHoleCoordinates
									+ qualityDataIndex.bridgeWidth
									+ qualityDataIndex.bridgeThickness
									+ qualityDataIndex.bridgeSlope
									+ qualityDataIndex.hardenedConcreteChlorideIonContent
									+ qualityDataIndex.hardenedConcreteAlkaliContent
									+ qualityDataIndex.concreteAdditives
									+ qualityDataIndex.anchorsPrestressing
									+ qualityDataIndex.plateRubberBearingInstallationQuality
									+ qualityDataIndex.deckTopSurfaceElevation
									+ qualityDataIndex.crackWidth
									+ qualityDataIndex.pileFoundation
									+ qualityDataIndex.bridgeEngineeringOthers )+ (qualityDataIndex.liningSupportConcreteStrength					
									+ qualityDataIndex.liningSupportLiningThickness							
									+ qualityDataIndex.liningSupportFlatness										
									+ qualityDataIndex.liningSupportAnchorDrawing								
									+ qualityDataIndex.anchorSpacingLengthGrouting				
									+ qualityDataIndex.clearance													
									+ qualityDataIndex.sectionOutline											
									+ qualityDataIndex.archesSpacing													
									+ qualityDataIndex.reinforcedSpacing											
									+ qualityDataIndex.empties																
									+ qualityDataIndex.waterproofBoardWeldingWide							
									+ qualityDataIndex.leadSmallNumIndirect										
									+ qualityDataIndex.tunnelPavement													
									+ qualityDataIndex.tunnelEngineeringOthers)+(qualityDataIndex.reinforced
									+ qualityDataIndex.cement
									+ qualityDataIndex.asphalt
									+ qualityDataIndex.lime
									+ qualityDataIndex.gravel
									+ qualityDataIndex.sand
									+ qualityDataIndex.mineralPowder
									+ qualityDataIndex.rubberBearings
									+ qualityDataIndex.anchor
									+ qualityDataIndex.splicingBolts
									+ qualityDataIndex.geotextile
									+ qualityDataIndex.waterproofBoard
									+ qualityDataIndex.drain
									+ qualityDataIndex.slurry
									+ qualityDataIndex.materialsOthers)+(qualityDataIndex.componentBaseThickness
										+ qualityDataIndex.componentCoatingThickness
										+ qualityDataIndex.fencebeamCenterHeight
										+ qualityDataIndex.fenceColumnEmbeddedDepth
										+ qualityDataIndex.stitchingBoltTensileStrength
										+ qualityDataIndex.pillarWallThickness
										+ qualityDataIndex.concreteFenceStrength
										+ qualityDataIndex.wavePlateThickness
										+ qualityDataIndex.signBoardClearance
										+ qualityDataIndex.signThickness
										+ qualityDataIndex.signReverseReflection
										+ qualityDataIndex.trafficOthers)+(qualityDataIndex.networkQuality						
										+ qualityDataIndex.coatingThickness					
										+ qualityDataIndex.insulationResistance			
										+ qualityDataIndex.groundingResistance			
										+ qualityDataIndex.powerFactors							
										+ qualityDataIndex.blowerClearance			
										+ qualityDataIndex.meOthers)}</td>
						<td>${(qualityDataIndex.earthworkCompactionPassed
									+ qualityDataIndex.earthworkDeflectionPassed
									+ qualityDataIndex.earthworkAshDosagePassed		
									+ qualityDataIndex.drainageSectionSizePassed
									+ qualityDataIndex.drainagePavedThicknessPassed	
									+ qualityDataIndex.bridgeConcreteStrengthPassed		
									+ qualityDataIndex.bridgeReinforcedThicknessPassed
									+ qualityDataIndex.bridgeStructureSizePassed
									+ qualityDataIndex.bridgeCulvertSpacingPassed
									+ qualityDataIndex.bridgeCulvertElevationPositionPassed	
									+ qualityDataIndex.supportConcreteStrengthPassed
									+ qualityDataIndex.supportSectionSizePassed	
									+ qualityDataIndex.subgradeEngineeringOthersPassed) + (qualityDataIndex.surfaceCompactionPassed
									+ qualityDataIndex.surfaceConcreteRoadStrengthPassed
									+ qualityDataIndex.surfaceDeflectionPassed
									+ qualityDataIndex.surfaceAsphaltContentPassed
									+ qualityDataIndex.surfacePorosityPassed
									+ qualityDataIndex.surfaceThicknessPassed
									+ qualityDataIndex.surfaceFlatnessPassed
									+ qualityDataIndex.surfaceCrossSlopePassed
									+ qualityDataIndex.surfaceElevationPassed
									+ qualityDataIndex.surfaceWaterSeepageCoefficientPassed
									+ qualityDataIndex.surfaceSlabHeightDifferencePassed
									+ qualityDataIndex.surfaceAntiSlipSurfacePassed
									+ qualityDataIndex.grassrootsLevelCompactionPassed
									+ qualityDataIndex.grassrootsSubbaseGrayAmountPassed
									+ qualityDataIndex.grassrootsThicknessPassed
									+ qualityDataIndex.grassrootsUnitIntegrityPassed
									+ qualityDataIndex.grassrootsIntensityPassed
									+ qualityDataIndex.grassrootsLevelCracksPassed
									+ qualityDataIndex.grassrootsCrossSlopePassed
									+ qualityDataIndex.grassrootsElevationPassed
									+ qualityDataIndex.pavementEngineeringOthersPassed )+(qualityDataIndex.anchorsPrestressingPassed
									+ qualityDataIndex.bridgeEngineeringOthersPassed
									+ qualityDataIndex.bridgeSlopePassed
									+ qualityDataIndex.bridgeThicknessPassed
									+ qualityDataIndex.bridgeWidthPassed
									+ qualityDataIndex.concreteAdditivesPassed
									+ qualityDataIndex.crackWidthPassed
									+ qualityDataIndex.deckTopSurfaceElevationPassed
									+ qualityDataIndex.hardenedConcreteAlkaliContentPassed
									+ qualityDataIndex.hardenedConcreteChlorideIonContentPassed
									+ qualityDataIndex.lowerstructureConcreteStrengthPassed
									+ qualityDataIndex.lowerstructureElevationPlaneLocationPassed
									+ qualityDataIndex.lowerstructurePierVerticalityPassed
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThicknessPassed
									+ qualityDataIndex.lowerstructureReinforcedSpacingPassed
									+ qualityDataIndex.lowerstructureStructureSizePassed
									+ qualityDataIndex.pileFoundationPassed
									+ qualityDataIndex.plateRubberBearingInstallationQualityPassed
									+ qualityDataIndex.superstructureConcreteStrengthPassed
									+ qualityDataIndex.superstructureElevationPlaneLocationPassed
									+ qualityDataIndex.superstructurePrestressedHoleCoordinatesPassed
									+ qualityDataIndex.superstructureReinforcedProtectiveThicknessPassed
									+ qualityDataIndex.superstructureReinforcedSpacingPassed
									+ qualityDataIndex.superstructureStructureSizePassed )+(qualityDataIndex.liningSupportConcreteStrengthPassed	
									+ qualityDataIndex.liningSupportLiningThicknessPassed
									+ qualityDataIndex.liningSupportFlatnessPassed
									+ qualityDataIndex.anchorSpacingLengthGroutingPassed
									+ qualityDataIndex.liningSupportAnchorDrawingPassed
									+ qualityDataIndex.clearancePassed
									+ qualityDataIndex.sectionOutlinePassed
									+ qualityDataIndex.archesSpacingPassed
									+ qualityDataIndex.reinforcedSpacingPassed
									+ qualityDataIndex.emptiesPassed
									+ qualityDataIndex.waterproofBoardWeldingWidePassed
									+ qualityDataIndex.leadSmallNumIndirectPassed
									+ qualityDataIndex.tunnelPavementPassed
									+ qualityDataIndex.tunnelEngineeringOthersPassed)+(qualityDataIndex.reinforcedPassed
									+ qualityDataIndex.cementPassed
									+ qualityDataIndex.asphaltPassed
									+ qualityDataIndex.limePassed
									+ qualityDataIndex.gravelsandPassed
									+ qualityDataIndex.sandPassed
									+ qualityDataIndex.mineralPowderPassed
									+ qualityDataIndex.rubberBearingsPassed
									+ qualityDataIndex.anchorPassed
									+ qualityDataIndex.splicingBoltsPassed
									+ qualityDataIndex.geotextilePassed
									+ qualityDataIndex.waterproofBoardPassed
									+ qualityDataIndex.drainPassed
									+ qualityDataIndex.slurryPassed
									+ qualityDataIndex.materialsOthersPassed)+(qualityDataIndex.componentBaseThicknessPassed
										+ qualityDataIndex.componentCoatingThicknessPassed
										+ qualityDataIndex.fencebeamCenterHeightPassed
										+ qualityDataIndex.fenceColumnEmbeddedDepthPassed
										+ qualityDataIndex.stitchingBoltTensileStrengthPassed
										+ qualityDataIndex.pillarWallThicknessPassed
										+ qualityDataIndex.concreteFenceStrengthPassed
										+ qualityDataIndex.wavePlateThicknessPassed
										+ qualityDataIndex.signBoardClearancePassed
										+ qualityDataIndex.signThicknessPassed
										+ qualityDataIndex.signReverseReflectionPassed
										+ qualityDataIndex.trafficOthersPassed )+(qualityDataIndex.networkQualityPassed
										+ qualityDataIndex.coatingThicknessPassed
										+ qualityDataIndex.insulationResistancePassed
										+ qualityDataIndex.groundingResistancePassed
										+ qualityDataIndex.powerFactorsPassed
										+ qualityDataIndex.blowerClearancePassed
										+ qualityDataIndex.meOthersPassed)}</td>
						<td><fmt:formatNumber type="number"
								value="${((qualityDataIndex.earthworkCompaction					
									+ qualityDataIndex.earthworkDeflection					
									+ qualityDataIndex.earthworkAshDosage						
									+ qualityDataIndex.drainageSectionSize					
									+ qualityDataIndex.drainagePavedThickness					
									+ qualityDataIndex.bridgeConcreteStrength					
									+ qualityDataIndex.bridgeReinforcedThickness				
									+ qualityDataIndex.bridgeStructureSize					
									+ qualityDataIndex.bridgeCulvertSpacing					
									+ qualityDataIndex.bridgeCulvertElevationPosition			
									+ qualityDataIndex.supportConcreteStrength				
									+ qualityDataIndex.supportSectionSize						
									+ qualityDataIndex.subgradeEngineeringOthers)+ (qualityDataIndex.surfaceCompaction
									+ qualityDataIndex.surfaceConcreteRoadStrength
									+ qualityDataIndex.surfaceDeflection
									+ qualityDataIndex.surfaceAsphaltContent
									+ qualityDataIndex.surfacePorosity
									+ qualityDataIndex.surfaceThickness
									+ qualityDataIndex.surfaceFlatness
									+ qualityDataIndex.surfaceCrossSlope
									+ qualityDataIndex.surfaceElevation
									+ qualityDataIndex.surfaceWaterSeepageCoefficient
									+ qualityDataIndex.surfaceSlabHeightDifference
									+ qualityDataIndex.surfaceAntiSlipSurface
									+ qualityDataIndex.grassrootsLevelCompaction
									+ qualityDataIndex.grassrootsSubbaseGrayAmount
									+ qualityDataIndex.grassrootsThickness
									+ qualityDataIndex.grassrootsUnitIntegrity
									+ qualityDataIndex.grassrootsIntensity
									+ qualityDataIndex.grassrootsLevelCracks
									+ qualityDataIndex.grassrootsCrossSlope
									+ qualityDataIndex.grassrootsElevation
									+ qualityDataIndex.pavementEngineeringOthers) + (qualityDataIndex.lowerstructureConcreteStrength
									+ qualityDataIndex.lowerstructurePierVerticality
									+ qualityDataIndex.lowerstructureStructureSize
									+ qualityDataIndex.lowerstructureReinforcedSpacing
									+ qualityDataIndex.lowerstructureElevationPlaneLocation
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureConcreteStrength
									+ qualityDataIndex.superstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureStructureSize
									+ qualityDataIndex.superstructureReinforcedSpacing
									+ qualityDataIndex.superstructureElevationPlaneLocation
									+ qualityDataIndex.superstructurePrestressedHoleCoordinates
									+ qualityDataIndex.bridgeWidth
									+ qualityDataIndex.bridgeThickness
									+ qualityDataIndex.bridgeSlope
									+ qualityDataIndex.hardenedConcreteChlorideIonContent
									+ qualityDataIndex.hardenedConcreteAlkaliContent
									+ qualityDataIndex.concreteAdditives
									+ qualityDataIndex.anchorsPrestressing
									+ qualityDataIndex.plateRubberBearingInstallationQuality
									+ qualityDataIndex.deckTopSurfaceElevation
									+ qualityDataIndex.crackWidth
									+ qualityDataIndex.pileFoundation
									+ qualityDataIndex.bridgeEngineeringOthers )+ (qualityDataIndex.liningSupportConcreteStrength					
									+ qualityDataIndex.liningSupportLiningThickness							
									+ qualityDataIndex.liningSupportFlatness										
									+ qualityDataIndex.liningSupportAnchorDrawing								
									+ qualityDataIndex.anchorSpacingLengthGrouting				
									+ qualityDataIndex.clearance													
									+ qualityDataIndex.sectionOutline											
									+ qualityDataIndex.archesSpacing													
									+ qualityDataIndex.reinforcedSpacing											
									+ qualityDataIndex.empties																
									+ qualityDataIndex.waterproofBoardWeldingWide							
									+ qualityDataIndex.leadSmallNumIndirect										
									+ qualityDataIndex.tunnelPavement													
									+ qualityDataIndex.tunnelEngineeringOthers)+(qualityDataIndex.reinforced
									+ qualityDataIndex.cement
									+ qualityDataIndex.asphalt
									+ qualityDataIndex.lime
									+ qualityDataIndex.gravel
									+ qualityDataIndex.sand
									+ qualityDataIndex.mineralPowder
									+ qualityDataIndex.rubberBearings
									+ qualityDataIndex.anchor
									+ qualityDataIndex.splicingBolts
									+ qualityDataIndex.geotextile
									+ qualityDataIndex.waterproofBoard
									+ qualityDataIndex.drain
									+ qualityDataIndex.slurry
									+ qualityDataIndex.materialsOthers)+(qualityDataIndex.componentBaseThickness
										+ qualityDataIndex.componentCoatingThickness
										+ qualityDataIndex.fencebeamCenterHeight
										+ qualityDataIndex.fenceColumnEmbeddedDepth
										+ qualityDataIndex.stitchingBoltTensileStrength
										+ qualityDataIndex.pillarWallThickness
										+ qualityDataIndex.concreteFenceStrength
										+ qualityDataIndex.wavePlateThickness
										+ qualityDataIndex.signBoardClearance
										+ qualityDataIndex.signThickness
										+ qualityDataIndex.signReverseReflection
										+ qualityDataIndex.trafficOthers)+(qualityDataIndex.networkQuality						
										+ qualityDataIndex.coatingThickness					
										+ qualityDataIndex.insulationResistance			
										+ qualityDataIndex.groundingResistance			
										+ qualityDataIndex.powerFactors							
										+ qualityDataIndex.blowerClearance			
										+ qualityDataIndex.meOthers)) == 0 ? 0:((qualityDataIndex.earthworkCompactionPassed
									+ qualityDataIndex.earthworkDeflectionPassed
									+ qualityDataIndex.earthworkAshDosagePassed		
									+ qualityDataIndex.drainageSectionSizePassed
									+ qualityDataIndex.drainagePavedThicknessPassed	
									+ qualityDataIndex.bridgeConcreteStrengthPassed		
									+ qualityDataIndex.bridgeReinforcedThicknessPassed
									+ qualityDataIndex.bridgeStructureSizePassed
									+ qualityDataIndex.bridgeCulvertSpacingPassed
									+ qualityDataIndex.bridgeCulvertElevationPositionPassed	
									+ qualityDataIndex.supportConcreteStrengthPassed
									+ qualityDataIndex.supportSectionSizePassed	
									+ qualityDataIndex.subgradeEngineeringOthersPassed) + (qualityDataIndex.surfaceCompactionPassed
									+ qualityDataIndex.surfaceConcreteRoadStrengthPassed
									+ qualityDataIndex.surfaceDeflectionPassed
									+ qualityDataIndex.surfaceAsphaltContentPassed
									+ qualityDataIndex.surfacePorosityPassed
									+ qualityDataIndex.surfaceThicknessPassed
									+ qualityDataIndex.surfaceFlatnessPassed
									+ qualityDataIndex.surfaceCrossSlopePassed
									+ qualityDataIndex.surfaceElevationPassed
									+ qualityDataIndex.surfaceWaterSeepageCoefficientPassed
									+ qualityDataIndex.surfaceSlabHeightDifferencePassed
									+ qualityDataIndex.surfaceAntiSlipSurfacePassed
									+ qualityDataIndex.grassrootsLevelCompactionPassed
									+ qualityDataIndex.grassrootsSubbaseGrayAmountPassed
									+ qualityDataIndex.grassrootsThicknessPassed
									+ qualityDataIndex.grassrootsUnitIntegrityPassed
									+ qualityDataIndex.grassrootsIntensityPassed
									+ qualityDataIndex.grassrootsLevelCracksPassed
									+ qualityDataIndex.grassrootsCrossSlopePassed
									+ qualityDataIndex.grassrootsElevationPassed
									+ qualityDataIndex.pavementEngineeringOthersPassed )+(qualityDataIndex.anchorsPrestressingPassed
									+ qualityDataIndex.bridgeEngineeringOthersPassed
									+ qualityDataIndex.bridgeSlopePassed
									+ qualityDataIndex.bridgeThicknessPassed
									+ qualityDataIndex.bridgeWidthPassed
									+ qualityDataIndex.concreteAdditivesPassed
									+ qualityDataIndex.crackWidthPassed
									+ qualityDataIndex.deckTopSurfaceElevationPassed
									+ qualityDataIndex.hardenedConcreteAlkaliContentPassed
									+ qualityDataIndex.hardenedConcreteChlorideIonContentPassed
									+ qualityDataIndex.lowerstructureConcreteStrengthPassed
									+ qualityDataIndex.lowerstructureElevationPlaneLocationPassed
									+ qualityDataIndex.lowerstructurePierVerticalityPassed
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThicknessPassed
									+ qualityDataIndex.lowerstructureReinforcedSpacingPassed
									+ qualityDataIndex.lowerstructureStructureSizePassed
									+ qualityDataIndex.pileFoundationPassed
									+ qualityDataIndex.plateRubberBearingInstallationQualityPassed
									+ qualityDataIndex.superstructureConcreteStrengthPassed
									+ qualityDataIndex.superstructureElevationPlaneLocationPassed
									+ qualityDataIndex.superstructurePrestressedHoleCoordinatesPassed
									+ qualityDataIndex.superstructureReinforcedProtectiveThicknessPassed
									+ qualityDataIndex.superstructureReinforcedSpacingPassed
									+ qualityDataIndex.superstructureStructureSizePassed )+(qualityDataIndex.liningSupportConcreteStrengthPassed	
									+ qualityDataIndex.liningSupportLiningThicknessPassed
									+ qualityDataIndex.liningSupportFlatnessPassed
									+ qualityDataIndex.anchorSpacingLengthGroutingPassed
									+ qualityDataIndex.liningSupportAnchorDrawingPassed
									+ qualityDataIndex.clearancePassed
									+ qualityDataIndex.sectionOutlinePassed
									+ qualityDataIndex.archesSpacingPassed
									+ qualityDataIndex.reinforcedSpacingPassed
									+ qualityDataIndex.emptiesPassed
									+ qualityDataIndex.waterproofBoardWeldingWidePassed
									+ qualityDataIndex.leadSmallNumIndirectPassed
									+ qualityDataIndex.tunnelPavementPassed
									+ qualityDataIndex.tunnelEngineeringOthersPassed)+(qualityDataIndex.reinforcedPassed
									+ qualityDataIndex.cementPassed
									+ qualityDataIndex.asphaltPassed
									+ qualityDataIndex.limePassed
									+ qualityDataIndex.gravelsandPassed
									+ qualityDataIndex.sandPassed
									+ qualityDataIndex.mineralPowderPassed
									+ qualityDataIndex.rubberBearingsPassed
									+ qualityDataIndex.anchorPassed
									+ qualityDataIndex.splicingBoltsPassed
									+ qualityDataIndex.geotextilePassed
									+ qualityDataIndex.waterproofBoardPassed
									+ qualityDataIndex.drainPassed
									+ qualityDataIndex.slurryPassed
									+ qualityDataIndex.materialsOthersPassed)+(qualityDataIndex.componentBaseThicknessPassed
										+ qualityDataIndex.componentCoatingThicknessPassed
										+ qualityDataIndex.fencebeamCenterHeightPassed
										+ qualityDataIndex.fenceColumnEmbeddedDepthPassed
										+ qualityDataIndex.stitchingBoltTensileStrengthPassed
										+ qualityDataIndex.pillarWallThicknessPassed
										+ qualityDataIndex.concreteFenceStrengthPassed
										+ qualityDataIndex.wavePlateThicknessPassed
										+ qualityDataIndex.signBoardClearancePassed
										+ qualityDataIndex.signThicknessPassed
										+ qualityDataIndex.signReverseReflectionPassed
										+ qualityDataIndex.trafficOthersPassed )+(qualityDataIndex.networkQualityPassed
										+ qualityDataIndex.coatingThicknessPassed
										+ qualityDataIndex.insulationResistancePassed
										+ qualityDataIndex.groundingResistancePassed
										+ qualityDataIndex.powerFactorsPassed
										+ qualityDataIndex.blowerClearancePassed
										+ qualityDataIndex.meOthersPassed))/((qualityDataIndex.earthworkCompaction					
									+ qualityDataIndex.earthworkDeflection					
									+ qualityDataIndex.earthworkAshDosage						
									+ qualityDataIndex.drainageSectionSize					
									+ qualityDataIndex.drainagePavedThickness					
									+ qualityDataIndex.bridgeConcreteStrength					
									+ qualityDataIndex.bridgeReinforcedThickness				
									+ qualityDataIndex.bridgeStructureSize					
									+ qualityDataIndex.bridgeCulvertSpacing					
									+ qualityDataIndex.bridgeCulvertElevationPosition			
									+ qualityDataIndex.supportConcreteStrength				
									+ qualityDataIndex.supportSectionSize						
									+ qualityDataIndex.subgradeEngineeringOthers)+ (qualityDataIndex.surfaceCompaction
									+ qualityDataIndex.surfaceConcreteRoadStrength
									+ qualityDataIndex.surfaceDeflection
									+ qualityDataIndex.surfaceAsphaltContent
									+ qualityDataIndex.surfacePorosity
									+ qualityDataIndex.surfaceThickness
									+ qualityDataIndex.surfaceFlatness
									+ qualityDataIndex.surfaceCrossSlope
									+ qualityDataIndex.surfaceElevation
									+ qualityDataIndex.surfaceWaterSeepageCoefficient
									+ qualityDataIndex.surfaceSlabHeightDifference
									+ qualityDataIndex.surfaceAntiSlipSurface
									+ qualityDataIndex.grassrootsLevelCompaction
									+ qualityDataIndex.grassrootsSubbaseGrayAmount
									+ qualityDataIndex.grassrootsThickness
									+ qualityDataIndex.grassrootsUnitIntegrity
									+ qualityDataIndex.grassrootsIntensity
									+ qualityDataIndex.grassrootsLevelCracks
									+ qualityDataIndex.grassrootsCrossSlope
									+ qualityDataIndex.grassrootsElevation
									+ qualityDataIndex.pavementEngineeringOthers) + (qualityDataIndex.lowerstructureConcreteStrength
									+ qualityDataIndex.lowerstructurePierVerticality
									+ qualityDataIndex.lowerstructureStructureSize
									+ qualityDataIndex.lowerstructureReinforcedSpacing
									+ qualityDataIndex.lowerstructureElevationPlaneLocation
									+ qualityDataIndex.lowerstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureConcreteStrength
									+ qualityDataIndex.superstructureReinforcedProtectiveThickness
									+ qualityDataIndex.superstructureStructureSize
									+ qualityDataIndex.superstructureReinforcedSpacing
									+ qualityDataIndex.superstructureElevationPlaneLocation
									+ qualityDataIndex.superstructurePrestressedHoleCoordinates
									+ qualityDataIndex.bridgeWidth
									+ qualityDataIndex.bridgeThickness
									+ qualityDataIndex.bridgeSlope
									+ qualityDataIndex.hardenedConcreteChlorideIonContent
									+ qualityDataIndex.hardenedConcreteAlkaliContent
									+ qualityDataIndex.concreteAdditives
									+ qualityDataIndex.anchorsPrestressing
									+ qualityDataIndex.plateRubberBearingInstallationQuality
									+ qualityDataIndex.deckTopSurfaceElevation
									+ qualityDataIndex.crackWidth
									+ qualityDataIndex.pileFoundation
									+ qualityDataIndex.bridgeEngineeringOthers )+ (qualityDataIndex.liningSupportConcreteStrength					
									+ qualityDataIndex.liningSupportLiningThickness							
									+ qualityDataIndex.liningSupportFlatness										
									+ qualityDataIndex.liningSupportAnchorDrawing								
									+ qualityDataIndex.anchorSpacingLengthGrouting				
									+ qualityDataIndex.clearance													
									+ qualityDataIndex.sectionOutline											
									+ qualityDataIndex.archesSpacing													
									+ qualityDataIndex.reinforcedSpacing											
									+ qualityDataIndex.empties																
									+ qualityDataIndex.waterproofBoardWeldingWide							
									+ qualityDataIndex.leadSmallNumIndirect										
									+ qualityDataIndex.tunnelPavement													
									+ qualityDataIndex.tunnelEngineeringOthers)+(qualityDataIndex.reinforced
									+ qualityDataIndex.cement
									+ qualityDataIndex.asphalt
									+ qualityDataIndex.lime
									+ qualityDataIndex.gravel
									+ qualityDataIndex.sand
									+ qualityDataIndex.mineralPowder
									+ qualityDataIndex.rubberBearings
									+ qualityDataIndex.anchor
									+ qualityDataIndex.splicingBolts
									+ qualityDataIndex.geotextile
									+ qualityDataIndex.waterproofBoard
									+ qualityDataIndex.drain
									+ qualityDataIndex.slurry
									+ qualityDataIndex.materialsOthers)+(qualityDataIndex.componentBaseThickness
										+ qualityDataIndex.componentCoatingThickness
										+ qualityDataIndex.fencebeamCenterHeight
										+ qualityDataIndex.fenceColumnEmbeddedDepth
										+ qualityDataIndex.stitchingBoltTensileStrength
										+ qualityDataIndex.pillarWallThickness
										+ qualityDataIndex.concreteFenceStrength
										+ qualityDataIndex.wavePlateThickness
										+ qualityDataIndex.signBoardClearance
										+ qualityDataIndex.signThickness
										+ qualityDataIndex.signReverseReflection
										+ qualityDataIndex.trafficOthers)+(qualityDataIndex.networkQuality						
										+ qualityDataIndex.coatingThickness					
										+ qualityDataIndex.insulationResistance			
										+ qualityDataIndex.groundingResistance			
										+ qualityDataIndex.powerFactors							
										+ qualityDataIndex.blowerClearance			
										+ qualityDataIndex.meOthers))* 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
					<tr>
						<td colspan="3">关键指标</td>
						<td>${qualityDataIndex.earthworkCompaction 
							+ qualityDataIndex.earthworkDeflection 
							+ qualityDataIndex.bridgeConcreteStrength 
							+ qualityDataIndex.supportConcreteStrength 
							+ qualityDataIndex.supportSectionSize
							+ qualityDataIndex.surfaceCompaction
							+ qualityDataIndex.surfaceConcreteRoadStrength
							+ qualityDataIndex.surfaceDeflection
						    + qualityDataIndex.surfaceThickness
							+ qualityDataIndex.surfaceFlatness
							+ qualityDataIndex.grassrootsLevelCompaction
						    + qualityDataIndex.grassrootsUnitIntegrity 
							+ qualityDataIndex.grassrootsIntensity 
							+ qualityDataIndex.lowerstructureConcreteStrength 
							+ qualityDataIndex.superstructureConcreteStrength 
							+ qualityDataIndex.bridgeThickness 
							+ qualityDataIndex.bridgeSlope 
							+ qualityDataIndex.liningSupportConcreteStrength 
							+ qualityDataIndex.liningSupportLiningThickness 
							+ qualityDataIndex.anchorSpacingLengthGrouting 
							+ qualityDataIndex.clearance 
							+ qualityDataIndex.reinforced 
							+ qualityDataIndex.cement 
							+ qualityDataIndex.asphalt 
							+ qualityDataIndex.lime 
							+ qualityDataIndex.gravel 
							+ qualityDataIndex.sand 
							+ qualityDataIndex.mineralPowder 
							+ qualityDataIndex.rubberBearings 
							+ qualityDataIndex.anchor 
							+ qualityDataIndex.splicingBolts 
							+ qualityDataIndex.geotextile 
							+ qualityDataIndex.waterproofBoard 
							+ qualityDataIndex.drain 
							+ qualityDataIndex.slurry 
							+ qualityDataIndex.materialsOthers 
							+ qualityDataIndex.fencebeamCenterHeight 
							+ qualityDataIndex.pillarWallThickness 
							+ qualityDataIndex.concreteFenceStrength 
							+ qualityDataIndex.wavePlateThickness }</td>
						<td>${qualityDataIndex.earthworkCompactionPassed 
							+ qualityDataIndex.earthworkDeflectionPassed 
							+ qualityDataIndex.bridgeConcreteStrengthPassed 
							+ qualityDataIndex.supportConcreteStrengthPassed 
							+ qualityDataIndex.supportSectionSizePassed 
							+ qualityDataIndex.surfaceCompactionPassed 
							+ qualityDataIndex.surfaceConcreteRoadStrengthPassed 
							+ qualityDataIndex.surfaceDeflectionPassed 
							+ qualityDataIndex.surfaceThicknessPassed 
							+ qualityDataIndex.surfaceFlatnessPassed 
							+ qualityDataIndex.grassrootsLevelCompactionPassed 
							+ qualityDataIndex.grassrootsUnitIntegrityPassed 
							+ qualityDataIndex.grassrootsIntensityPassed 
							+ qualityDataIndex.lowerstructureConcreteStrengthPassed 
							+ qualityDataIndex.superstructureConcreteStrengthPassed 
							+ qualityDataIndex.bridgeThicknessPassed 
							+ qualityDataIndex.bridgeSlopePassed 
							+ qualityDataIndex.liningSupportConcreteStrengthPassed 
							+ qualityDataIndex.liningSupportLiningThicknessPassed 
							+ qualityDataIndex.anchorSpacingLengthGroutingPassed 
							+ qualityDataIndex.clearancePassed 
							+ qualityDataIndex.reinforcedPassed 
							+ qualityDataIndex.cementPassed  
							+ qualityDataIndex.asphaltPassed 
							+ qualityDataIndex.limePassed 
							+ qualityDataIndex.gravelsandPassed 
							+ qualityDataIndex.sandPassed 
							+ qualityDataIndex.mineralPowderPassed 
							+ qualityDataIndex.rubberBearingsPassed 
							+ qualityDataIndex.anchorPassed 
							+ qualityDataIndex.splicingBoltsPassed 
							+ qualityDataIndex.geotextilePassed 
							+ qualityDataIndex.waterproofBoardPassed 
							+ qualityDataIndex.drainPassed 
							+ qualityDataIndex.slurryPassed 
							+ qualityDataIndex.materialsOthersPassed 
							+ qualityDataIndex.fencebeamCenterHeightPassed 
							+ qualityDataIndex.pillarWallThicknessPassed  
							+ qualityDataIndex.concreteFenceStrengthPassed 
							+ qualityDataIndex.wavePlateThicknessPassed }</td>
						<td><fmt:formatNumber type="number"
								value="${(qualityDataIndex.earthworkCompaction 
							+ qualityDataIndex.earthworkDeflection 
							+ qualityDataIndex.bridgeConcreteStrength 
							+ qualityDataIndex.supportConcreteStrength 
							+ qualityDataIndex.supportSectionSize
							+ qualityDataIndex.surfaceCompaction
							+ qualityDataIndex.surfaceConcreteRoadStrength
							+ qualityDataIndex.surfaceDeflection
						    + qualityDataIndex.surfaceThickness
							+ qualityDataIndex.surfaceFlatness
							+ qualityDataIndex.grassrootsLevelCompaction
						    + qualityDataIndex.grassrootsUnitIntegrity 
							+ qualityDataIndex.grassrootsIntensity 
							+ qualityDataIndex.lowerstructureConcreteStrength 
							+ qualityDataIndex.superstructureConcreteStrength 
							+ qualityDataIndex.bridgeThickness 
							+ qualityDataIndex.bridgeSlope 
							+ qualityDataIndex.liningSupportConcreteStrength 
							+ qualityDataIndex.liningSupportLiningThickness 
							+ qualityDataIndex.anchorSpacingLengthGrouting 
							+ qualityDataIndex.clearance 
							+ qualityDataIndex.reinforced 
							+ qualityDataIndex.cement 
							+ qualityDataIndex.asphalt 
							+ qualityDataIndex.lime 
							+ qualityDataIndex.gravel 
							+ qualityDataIndex.sand 
							+ qualityDataIndex.mineralPowder 
							+ qualityDataIndex.rubberBearings 
							+ qualityDataIndex.anchor 
							+ qualityDataIndex.splicingBolts 
							+ qualityDataIndex.geotextile 
							+ qualityDataIndex.waterproofBoard 
							+ qualityDataIndex.drain 
							+ qualityDataIndex.slurry 
							+ qualityDataIndex.materialsOthers 
							+ qualityDataIndex.fencebeamCenterHeight 
							+ qualityDataIndex.pillarWallThickness 
							+ qualityDataIndex.concreteFenceStrength 
							+ qualityDataIndex.wavePlateThickness )== 0 ? 0 :((qualityDataIndex.earthworkCompactionPassed 
							+ qualityDataIndex.earthworkDeflectionPassed 
							+ qualityDataIndex.bridgeConcreteStrengthPassed 
							+ qualityDataIndex.supportConcreteStrengthPassed 
							+ qualityDataIndex.supportSectionSizePassed 
							+ qualityDataIndex.surfaceCompactionPassed 
							+ qualityDataIndex.surfaceConcreteRoadStrengthPassed 
							+ qualityDataIndex.surfaceDeflectionPassed 
							+ qualityDataIndex.surfaceThicknessPassed 
							+ qualityDataIndex.surfaceFlatnessPassed 
							+ qualityDataIndex.grassrootsLevelCompactionPassed 
							+ qualityDataIndex.grassrootsUnitIntegrityPassed 
							+ qualityDataIndex.grassrootsIntensityPassed 
							+ qualityDataIndex.lowerstructureConcreteStrengthPassed 
							+ qualityDataIndex.superstructureConcreteStrengthPassed 
							+ qualityDataIndex.bridgeThicknessPassed 
							+ qualityDataIndex.bridgeSlopePassed 
							+ qualityDataIndex.liningSupportConcreteStrengthPassed 
							+ qualityDataIndex.liningSupportLiningThicknessPassed 
							+ qualityDataIndex.anchorSpacingLengthGroutingPassed 
							+ qualityDataIndex.clearancePassed 
							+ qualityDataIndex.reinforcedPassed 
							+ qualityDataIndex.cementPassed  
							+ qualityDataIndex.asphaltPassed 
							+ qualityDataIndex.limePassed 
							+ qualityDataIndex.gravelsandPassed 
							+ qualityDataIndex.sandPassed 
							+ qualityDataIndex.mineralPowderPassed 
							+ qualityDataIndex.rubberBearingsPassed 
							+ qualityDataIndex.anchorPassed 
							+ qualityDataIndex.splicingBoltsPassed 
							+ qualityDataIndex.geotextilePassed 
							+ qualityDataIndex.waterproofBoardPassed 
							+ qualityDataIndex.drainPassed 
							+ qualityDataIndex.slurryPassed 
							+ qualityDataIndex.materialsOthersPassed 
							+ qualityDataIndex.fencebeamCenterHeightPassed 
							+ qualityDataIndex.pillarWallThicknessPassed  
							+ qualityDataIndex.concreteFenceStrengthPassed 
							+ qualityDataIndex.wavePlateThicknessPassed)/(qualityDataIndex.earthworkCompaction 
							+ qualityDataIndex.earthworkDeflection 
							+ qualityDataIndex.bridgeConcreteStrength 
							+ qualityDataIndex.supportConcreteStrength 
							+ qualityDataIndex.supportSectionSize
							+ qualityDataIndex.surfaceCompaction
							+ qualityDataIndex.surfaceConcreteRoadStrength
							+ qualityDataIndex.surfaceDeflection
						    + qualityDataIndex.surfaceThickness
							+ qualityDataIndex.surfaceFlatness
							+ qualityDataIndex.grassrootsLevelCompaction
						    + qualityDataIndex.grassrootsUnitIntegrity 
							+ qualityDataIndex.grassrootsIntensity 
							+ qualityDataIndex.lowerstructureConcreteStrength 
							+ qualityDataIndex.superstructureConcreteStrength 
							+ qualityDataIndex.bridgeThickness 
							+ qualityDataIndex.bridgeSlope 
							+ qualityDataIndex.liningSupportConcreteStrength 
							+ qualityDataIndex.liningSupportLiningThickness 
							+ qualityDataIndex.anchorSpacingLengthGrouting 
							+ qualityDataIndex.clearance 
							+ qualityDataIndex.reinforced 
							+ qualityDataIndex.cement 
							+ qualityDataIndex.asphalt 
							+ qualityDataIndex.lime 
							+ qualityDataIndex.gravel 
							+ qualityDataIndex.sand 
							+ qualityDataIndex.mineralPowder 
							+ qualityDataIndex.rubberBearings 
							+ qualityDataIndex.anchor 
							+ qualityDataIndex.splicingBolts 
							+ qualityDataIndex.geotextile 
							+ qualityDataIndex.waterproofBoard 
							+ qualityDataIndex.drain 
							+ qualityDataIndex.slurry 
							+ qualityDataIndex.materialsOthers 
							+ qualityDataIndex.fencebeamCenterHeight 
							+ qualityDataIndex.pillarWallThickness 
							+ qualityDataIndex.concreteFenceStrength 
							+ qualityDataIndex.wavePlateThickness )) * 100}"
								pattern="0.00" maxFractionDigits="2" />%</td>
					</tr>
				</tbody>
			</c:otherwise>
		</c:choose>
	</table>
	<div class="form-actions">
		<a id="btnSubmit" class="btn btn-button" href="${ctx }/quality/qualityDataIndex/export?id=${qualityDataIndex.id}">导 出</a>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
	</div>
</body>
</html>