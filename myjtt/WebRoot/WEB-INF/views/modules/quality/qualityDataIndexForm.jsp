<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>质量检测统计数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		function pickedFunc(){  
	        $("#dateForYear").val( $(this).val() );
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/quality/qualityDataIndex/">质量检测统计数据列表</a></li>
		<li class="active"><a href="${ctx}/quality/qualityDataIndex/form?id=${qualityDataIndex.id}">质量检测统计数据<shiro:hasPermission name="quality:qualityDataIndex:edit">${not empty qualityDataIndex.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="quality:qualityDataIndex:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="qualityDataIndex" action="${ctx}/quality/qualityDataIndex/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<td style="text-align: center">项目名称</td>
					<td>
						<form:select path="proSimpleInfo.id" class="input-large required" style="width: 284px;">
							<form:option value="" label="--请选择项目--"/>
							<c:forEach items="${projectList}" var="var">
								<form:option value="${var.id }" label="${var.projectSimpleName }"/>
							</c:forEach>
						</form:select>
					</td>
					<td style="text-align: center">项目标段&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<form:input path="projectLabel" htmlEscape="false" maxlength="11" class="input-large required" /></td>
					<td style="text-align: center">检查时间</td>
					<td>
						<form:hidden path="checkDate" id="dateForYear" class="Wdate"/>
						<input name="checkDateForYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width: 60px;"
							value="<fmt:formatDate value="${qualityDataIndex.checkDateForYear}" pattern="yyyy"/>"
							onclick="WdatePicker({dateFmt:'yyyy', isShowClear:false, onpicked:pickedFunc});"/>
						<form:select path="checkDateSeason" class="input-large required" style="width: 100px;">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('check_date_season')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="text-align: center">抽查指标</td>
					<td style="text-align: center">检测点</td>
					<td style="text-align: center">合格点</td>
				</tr>
			</thead>
			<tbody>
				<!-- 路基工程start -->
				<tr>
					<td rowspan="13">路基工程</td>
					<td rowspan="3">土石方</td>
					<td>压实度*</td>
					<td><form:input path="earthworkCompaction" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="earthworkCompactionPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>弯沉*</td>
					<td><form:input path="earthworkDeflection" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="earthworkDeflectionPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>灰剂量</td>
					<td><form:input path="earthworkAshDosage" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="earthworkAshDosagePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td rowspan="2">排水</td>
					<td>断面尺寸</td>
					<td><form:input path="drainageSectionSize" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="drainageSectionSizePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>铺砌厚度</td>
					<td><form:input path="drainagePavedThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="drainagePavedThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td rowspan="5">小桥涵</td>
					<td>砼强度*</td>
					<td><form:input path="bridgeConcreteStrength" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="bridgeConcreteStrengthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>钢筋保护层厚度</td>
					<td><form:input path="bridgeReinforcedThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="bridgeReinforcedThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>结构尺寸</td>
					<td><form:input path="bridgeStructureSize" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="bridgeStructureSizePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>钢筋间距</td>
					<td><form:input path="bridgeCulvertSpacing" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="bridgeCulvertSpacingPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>高程及平面位置</td>
					<td><form:input path="bridgeCulvertElevationPosition" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="bridgeCulvertElevationPositionPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td rowspan="2">支挡工程</td>
					<td>砼强度*</td>
					<td><form:input path="supportConcreteStrength" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="supportConcreteStrengthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>断面尺寸*</td>
					<td><form:input path="supportSectionSize" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="supportSectionSizePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">其他</td>
					<td><form:input path="subgradeEngineeringOthers" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="subgradeEngineeringOthersPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				
				<!-- 路面工程start -->
				<tr>
					<td rowspan="21">路面工程</td>
					<td rowspan="12">面层</td>
					<td>压实度*</td>
					<td><form:input path="surfaceCompaction" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceCompactionPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>混凝土路面强度*</td>
					<td><form:input path="surfaceConcreteRoadStrength" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceConcreteRoadStrengthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>弯沉*</td>
					<td><form:input path="surfaceDeflection" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceDeflectionPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>沥青含量</td>
					<td><form:input path="surfaceAsphaltContent" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceAsphaltContentPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>空隙率</td>
					<td><form:input path="surfacePorosity" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfacePorosityPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>厚度*</td>
					<td><form:input path="surfaceThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>平整度*</td>
					<td><form:input path="surfaceFlatness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceFlatnessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>横坡</td>
					<td><form:input path="surfaceCrossSlope" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceCrossSlopePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>高程</td>
					<td><form:input path="surfaceElevation" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceElevationPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>渗水系数</td>
					<td><form:input path="surfaceWaterSeepageCoefficient" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceWaterSeepageCoefficientPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>混凝土路面相邻板高差</td>
					<td><form:input path="surfaceSlabHeightDifference" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceSlabHeightDifferencePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>路面抗滑</td>
					<td><form:input path="surfaceAntiSlipSurface" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="surfaceAntiSlipSurfacePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td rowspan="8">基层底基层</td>
					<td>压实度*</td>
					<td><form:input path="grassrootsLevelCompaction" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="grassrootsLevelCompactionPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>灰剂量</td>
					<td><form:input path="grassrootsSubbaseGrayAmount" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="grassrootsSubbaseGrayAmountPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>厚度</td>
					<td><form:input path="grassrootsThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="grassrootsThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>整体性*</td>
					<td><form:input path="grassrootsUnitIntegrity" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="grassrootsUnitIntegrityPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>强度*</td>
					<td><form:input path="grassrootsIntensity" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="grassrootsIntensityPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>基层裂缝</td>
					<td><form:input path="grassrootsLevelCracks" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="grassrootsLevelCracksPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>横坡</td>
					<td><form:input path="grassrootsCrossSlope" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="grassrootsCrossSlopePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>高程</td>
					<td><form:input path="grassrootsElevation" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="grassrootsElevationPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">其它	</td>
					<td><form:input path="pavementEngineeringOthers" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="pavementEngineeringOthersPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				
				<!-- 桥梁工程start -->
				<tr>
					<td rowspan="24">路面工程</td>
					<td rowspan="6">下部结构</td>
					<td>墩台砼强度*</td>
					<td><form:input path="lowerstructureConcreteStrength" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="lowerstructureConcreteStrengthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>钢筋保护层厚度</td>
					<td><form:input path="lowerstructureReinforcedProtectiveThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="lowerstructureReinforcedProtectiveThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>墩台垂直度</td>
					<td><form:input path="lowerstructurePierVerticality" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="lowerstructurePierVerticalityPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>结构尺寸</td>
					<td><form:input path="lowerstructureStructureSize" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="lowerstructureStructureSizePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>钢筋间距</td>
					<td><form:input path="lowerstructureReinforcedSpacing" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="lowerstructureReinforcedSpacingPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>高程及平面位置</td>
					<td><form:input path="lowerstructureElevationPlaneLocation" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="lowerstructureElevationPlaneLocationPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td rowspan="6">上部结构</td>
					<td>砼强度*</td>
					<td><form:input path="superstructureConcreteStrength" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="superstructureConcreteStrengthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>钢筋保护层厚度</td>
					<td><form:input path="superstructureReinforcedProtectiveThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="superstructureReinforcedProtectiveThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>结构尺寸</td>
					<td><form:input path="superstructureStructureSize" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="superstructureStructureSizePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>钢筋间距</td>
					<td><form:input path="superstructureReinforcedSpacing" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="superstructureReinforcedSpacingPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>高程及平面位置</td>
					<td><form:input path="superstructureElevationPlaneLocation" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="superstructureElevationPlaneLocationPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>预应力孔道坐标</td>
					<td><form:input path="superstructurePrestressedHoleCoordinates" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="superstructurePrestressedHoleCoordinatesPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">桥面宽度*	</td>
					<td><form:input path="bridgeWidth" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="bridgeWidthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">桥面厚度*	</td>
					<td><form:input path="bridgeThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="bridgeThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">桥面横坡*	</td>
					<td><form:input path="bridgeSlope" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="bridgeSlopePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">硬化混凝土氯离子含量</td>
					<td><form:input path="hardenedConcreteChlorideIonContent" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="hardenedConcreteChlorideIonContentPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">硬化混凝土碱含量</td>
					<td><form:input path="hardenedConcreteAlkaliContent" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="hardenedConcreteAlkaliContentPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">混凝土添加剂</td>
					<td><form:input path="concreteAdditives" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="concreteAdditivesPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">锚具及张拉预应力</td>
					<td><form:input path="anchorsPrestressing" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="anchorsPrestressingPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">板式橡胶支座及安装质量</td>
					<td><form:input path="plateRubberBearingInstallationQuality" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="plateRubberBearingInstallationQualityPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">桥面系顶面标高	</td>
					<td><form:input path="deckTopSurfaceElevation" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="deckTopSurfaceElevationPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">裂缝宽度</td>
					<td><form:input path="crackWidth" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="crackWidthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">桩基</td>
					<td><form:input path="pileFoundation" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="pileFoundationPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">其它</td>
					<td><form:input path="bridgeEngineeringOthers" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="bridgeEngineeringOthersPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				
				<!-- 隧道工程start -->
				<tr>
					<td rowspan="14">隧道工程</td>
					<td rowspan="4">衬砌支护</td>
					<td>砼强度*</td>
					<td><form:input path="liningSupportConcreteStrength" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="liningSupportConcreteStrengthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>衬砌厚度*</td>
					<td><form:input path="liningSupportLiningThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="liningSupportLiningThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>平整度</td>
					<td><form:input path="liningSupportFlatness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="liningSupportFlatnessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td>锚杆拉拔</td>
					<td><form:input path="liningSupportAnchorDrawing" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="liningSupportAnchorDrawingPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">锚杆间距、长度及注浆密实度*</td>
					<td><form:input path="anchorSpacingLengthGrouting" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="anchorSpacingLengthGroutingPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">净空*</td>
					<td><form:input path="clearance" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="clearancePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">断面轮廓</td>
					<td><form:input path="sectionOutline" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="sectionOutlinePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">拱架间距</td>
					<td><form:input path="archesSpacing" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="archesSpacingPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">钢筋间距</td>
					<td><form:input path="reinforcedSpacing" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="reinforcedSpacingPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">空洞</td>
					<td><form:input path="empties" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="emptiesPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">防水板质量搭接宽度</td>
					<td><form:input path="waterproofBoardWeldingWide" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="waterproofBoardWeldingWidePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">超前小导管数量或间距</td>
					<td><form:input path="leadSmallNumIndirect" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="leadSmallNumIndirectPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">隧道路面</td>
					<td><form:input path="tunnelPavement" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="tunnelPavementPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">其他</td>
					<td><form:input path="tunnelEngineeringOthers" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="tunnelEngineeringOthersPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				
				<!-- 原材料start -->
				<tr>
					<td rowspan="15">隧道工程</td>
					<td colspan="2">钢材*</td>
					<td><form:input path="reinforced" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="reinforcedPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">水泥*</td>
					<td><form:input path="cement" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="cementPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">沥青*</td>
					<td><form:input path="asphalt" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="asphaltPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">石灰*</td>
					<td><form:input path="lime" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="limePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">碎石*</td>
					<td><form:input path="gravel" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="gravelsandPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">砂*</td>
					<td><form:input path="sand" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="sandPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">矿粉*</td>
					<td><form:input path="mineralPowder" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="mineralPowderPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">橡胶支座*</td>
					<td><form:input path="rubberBearings" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="rubberBearingsPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">锚具*</td>
					<td><form:input path="anchor" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="anchorPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">拼接螺栓*</td>
					<td><form:input path="splicingBolts" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="splicingBoltsPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">土工布*</td>
					<td><form:input path="geotextile" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="geotextilePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">防水板*</td>
					<td><form:input path="waterproofBoard" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="waterproofBoardPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">排水管*</td>
					<td><form:input path="drain" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="drainPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">压浆料*</td>
					<td><form:input path="slurry" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="slurryPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">其他*</td>
					<td><form:input path="materialsOthers" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="materialsOthersPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				
				<!-- 交通安全设施start -->
				<tr>
					<td rowspan="12">交通安全设施</td>
					<td colspan="2">构件基底厚度</td>
					<td><form:input path="componentBaseThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="componentBaseThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">构件防腐层厚度</td>
					<td><form:input path="componentCoatingThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="componentCoatingThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">护栏横梁中心高度*</td>
					<td><form:input path="fencebeamCenterHeight" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="fencebeamCenterHeightPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">护栏立柱埋入深度</td>
					<td><form:input path="fenceColumnEmbeddedDepth" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="fenceColumnEmbeddedDepthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">拼接螺栓抗拉强度</td>
					<td><form:input path="stitchingBoltTensileStrength" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="stitchingBoltTensileStrengthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">立柱壁厚度*</td>
					<td><form:input path="pillarWallThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="pillarWallThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">混凝土护栏强度*</td>
					<td><form:input path="concreteFenceStrength" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="concreteFenceStrengthPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">波形板厚度*</td>
					<td><form:input path="wavePlateThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="wavePlateThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">标志板垂直度与净空</td>
					<td><form:input path="signBoardClearance" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="signBoardClearancePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">标线厚度</td>
					<td><form:input path="signThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="signThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">标线逆反射系数</td>
					<td><form:input path="signReverseReflection" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="signReverseReflectionPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">其他</td>
					<td><form:input path="trafficOthers" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="trafficOthersPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				
				<!-- 机电工程start -->
				<tr>
					<td rowspan="7">机电</td>
					<td colspan="2">网线质量</td>
					<td><form:input path="networkQuality" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="networkQualityPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">涂层厚度</td>
					<td><form:input path="coatingThickness" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="coatingThicknessPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">绝缘电阻</td>
					<td><form:input path="insulationResistance" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="insulationResistancePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">接地电阻</td>
					<td><form:input path="groundingResistance" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="groundingResistancePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">灯具功率因数</td>
					<td><form:input path="powerFactors" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="powerFactorsPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">风机安装净空</td>
					<td><form:input path="blowerClearance" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="blowerClearancePassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
				<tr>
					<td colspan="2">其他</td>
					<td><form:input path="meOthers" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					<td><form:input path="meOthersPassed" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/></td>
					
				</tr>
			</tbody>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="quality:qualityDataIndex:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>