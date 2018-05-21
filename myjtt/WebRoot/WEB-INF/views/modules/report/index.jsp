<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>首页</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/common/main.css"">
	<style type="text/css">
		.divcss{border:1px solid #DDDDDD} 
		body {
		    background: #fafafa;
		    color: #708090;
			font-family: Verdana, sans-serif, Arial;
			font-size: 10px;
		}
	</style>
	<script type="text/javascript" src="${ctxStatic}/chars/chart.js"></script>
	<script type="text/javascript" src="${ctxStatic}/common/ajax.js"></script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/report/home">首页</a></li>
	</ul>
	<div style="width:98%; margin-top: 5px; margin-left: 5px;">
        <div class="row">
          <div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Line Chart</h3>
              <div class="embed-responsive embed-responsive-16by9">
                <canvas class="embed-responsive-item" id="lineChartDemo"></canvas>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Bar Chart</h3>
              <div class="embed-responsive embed-responsive-16by9">
                <canvas class="embed-responsive-item" id="barChartDemo"></canvas>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
          <div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Pie Chart</h3>
              <div class="embed-responsive embed-responsive-16by9">
                <canvas class="embed-responsive-item" id="pieChartDemo"></canvas>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Doughnut Chart</h3>
              <div class="embed-responsive embed-responsive-16by9">
                <canvas class="embed-responsive-item" id="doughnutChartDemo"></canvas>
              </div>
            </div>
          </div>
        </div>
        
        <hr>
		
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<thead style="text-align: center">
						<tr><th style="text-align: center" colspan="6">项目安全生产汇总一览表</th></tr>
						<tr>
							<th style="text-align: center">项目名称</th>
							<th style="text-align: center">安全生产管理人员数</th>
							<th style="text-align: center">特种设备数</th>
							<th style="text-align: center">专用设备数</th>
							<th style="text-align: center">特种作业人员数</th>
							<th style="text-align: center">特种设备人员数</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty safeReportInfoList }">
							<c:forEach items="${safeReportInfoList}" var="projectSimpleInfo" varStatus="status">
								<tr class="default">
									<td style="text-align: center"> ${projectSimpleInfo.projectSimpleName} </td>
									<td style="text-align: center"> ${projectSimpleInfo.safeProductInfo.productUsers} </td>
									<td style="text-align: center"> ${projectSimpleInfo.safeProductInfo.specialSub} </td>
									<td style="text-align: center"> ${projectSimpleInfo.safeProductInfo.consSub} </td>
									<td style="text-align: center"> ${projectSimpleInfo.safeProductInfo.specialOperatorUsers} </td>
									<td style="text-align: center"> ${projectSimpleInfo.safeProductInfo.specialSubUsers} </td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty safeReportInfoList }">
							<tr><td  style="text-align: center" colspan="6">没有数据</td></tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		
		<hr>
		
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<thead style="text-align: center">
						<tr><th style="text-align: center" colspan="8">项目各单位信息汇总一览表</th></tr>
						<tr>
							<th style="text-align: center">项目名称</th>
							<th style="text-align: center">特殊路基个数 </th>
							<th style="text-align: center">建设单位个数</th>
							<th style="text-align: center">施工单位个数</th>
							<th style="text-align: center">监理单位个数</th>
							<th style="text-align: center">中心试验室个数</th>
							<th style="text-align: center">工地试验室个数</th>
							<th style="text-align: center">第三方检测单位个数</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty qualityReportInfoList }">
							<c:forEach items="${qualityReportInfoList}" var="projectSimpleInfo" varStatus="status">
								<tr class="default">
									<td style="text-align: center"> ${projectSimpleInfo.projectSimpleName} </td>
									<td style="text-align: center"> ${projectSimpleInfo.qualityReportInfo.specialSubNum} </td>
									<td style="text-align: center"> ${projectSimpleInfo.qualityReportInfo.buildCompanyNum} </td>
									<td style="text-align: center"> ${projectSimpleInfo.qualityReportInfo.constrCompanyNum} </td>
									<td style="text-align: center"> ${projectSimpleInfo.qualityReportInfo.supervisonCompanyNum} </td>
									<td style="text-align: center"> ${projectSimpleInfo.qualityReportInfo.testRoomNum} </td>
									<td style="text-align: center"> ${projectSimpleInfo.qualityReportInfo.constrTestRoomNum} </td>
									<td style="text-align: center"> ${projectSimpleInfo.qualityReportInfo.thirdCheckNum} </td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty qualityReportInfoList }">
							<tr><td  style="text-align: center" colspan="8">没有数据</td></tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		
		<hr>
		
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<thead>
						<tr><th style="text-align: center" colspan="10">高速公路基本信息一览表汇总</th></tr>
						<tr><th style="text-align: center">项目名称</th>
							<th style="text-align: center">项目类型</th>
							<th style="text-align: center">批准工期(月)</th>
							<th style="text-align: center">开工时间</th>
							<th style="text-align: center">设计时速(km/h)</th>
							<th style="text-align: center">建设里程(km)</th>
							<th style="text-align: center">施工合同段(个)</th>
							<th style="text-align: center">监理合同段(个)</th>
							<th style="text-align: center">桥梁累计长度(m)</th>
							<th style="text-align: center">隧道累计长度(m)</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty projectList }">
							<c:forEach items="${projectList}" var="projectSimpleInfo" varStatus="status">
								<tr class="default">
									<td style="text-align: center"> ${projectSimpleInfo.projectSimpleName} </td>
									<td style="text-align: center"> ${fns:getDictLabel(projectSimpleInfo.projectType, 'project_simple_type', '')}</td>
									<td style="text-align: center"> ${projectSimpleInfo.approveDuration} </td>
									<td style="text-align: center"> <fmt:formatDate value="${projectSimpleInfo.proposedStartTime}" pattern="yyyy-MM-dd"/></td>
									<td style="text-align: center"> ${projectSimpleInfo.designSpeed} </td>
									<td style="text-align: center"> ${projectSimpleInfo.buildMileage} </td>
									<td style="text-align: center"> ${projectSimpleInfo.proSimpleSupervision.contractSectionNum} </td>
									<td style="text-align: center"> ${projectSimpleInfo.proSimpleSupervision.supervisionSectionNum} </td>
									<td style="text-align: center"> ${projectSimpleInfo.proSimpleBridgeTunnel.bridgeTotalLength} </td>
									<td style="text-align: center"> ${projectSimpleInfo.proSimpleBridgeTunnel.tunnelTotalLength} </td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty projectList }">
							<tr><td  style="text-align: center" colspan="10">没有数据</td></tr>
						</c:if>
					<tbody>
				</table>
			</div>
		</div>
		<hr>
		
	</div>
    
    <script type="text/javascript">
    var data = {
          	labels: ["2018年第一季度", "第二季度", "第三季度", "第四季度"],
          	datasets: [
          		{
          			label: "My First dataset",
          			fillColor: "rgba(220,220,220,0.2)",
          			strokeColor: "rgba(220,220,220,1)",
          			pointColor: "rgba(220,220,220,1)",
          			pointStrokeColor: "#fff",
          			pointHighlightFill: "#fff",
          			pointHighlightStroke: "rgba(220,220,220,1)",
          			data: [65, 59, 80, 81]
          		},
          		{
          			label: "My Second dataset",
          			fillColor: "rgba(151,187,205,0.2)",
          			strokeColor: "rgba(151,187,205,1)",
          			pointColor: "rgba(151,187,205,1)",
          			pointStrokeColor: "#fff",
          			pointHighlightFill: "#fff",
          			pointHighlightStroke: "rgba(151,187,205,1)",
          			data: [28, 48, 40, 19]
          		}
          	]
          };
          var pdata = [
          	{
          		value: 300,
          		color:"#F7464A",
          		highlight: "#FF5A5E",
          		label: "Red"
          	},
          	{
          		value: 50,
          		color: "#46BFBD",
          		highlight: "#5AD3D1",
          		label: "Green"
          	},
          	{
          		value: 100,
          		color: "#FDB45C",
          		highlight: "#FFC870",
          		label: "Yellow"
          	}
          ]
      var ctxl = $("#lineChartDemo").get(0).getContext("2d");
      var lineChart = new Chart(ctxl).Line(data);
      
      var ctxb = $("#barChartDemo").get(0).getContext("2d");
      var barChart = new Chart(ctxb).Bar(data);
      
      var ctxp = $("#pieChartDemo").get(0).getContext("2d");
      var barChart = new Chart(ctxp).Pie(pdata);
      
      var ctxd = $("#doughnutChartDemo").get(0).getContext("2d");
      var barChart = new Chart(ctxd).Doughnut(pdata);
    </script>
    
</body>
</html>