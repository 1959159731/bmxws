<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/font/css/main.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/font/css/font-awesome.min.css">
    <title>陕西省交通厅</title>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries-->
    <!--if lt IE 9
    script(src='${ctxStatic}/font/js/html5shiv.js')
    script(src='${ctxStatic}/font/js/respond.min.js')
    -->
    <style type="text/css">  
		/*（我用的单位是%，根据实际用你的单位）*/  
		.tableCon {  
		    position: relative;  
		    width: 100%;  
		}  
		  
		.tableCon .tableFix {  
		    position: absolute;  
		    left: 0;  
		    top: 0;  
		    z-index: 1;  
		    width: 22%;  
		}  
		  
		.tableCon .tableScr {  
		    overflow: scroll;  
		    -webkit-overflow-scrolling: touch;  
		    /*滚动平滑*/  
		}  
		table {
		   font-size:10px;
		}
		
		table td {
			white-space:nowrap;  
	        overflow:hidden;  
	        word-break:keep-all;
		}
	</style>  
  </head>
  <body class="sidebar-mini fixed">
    <div class="wrapper">
      <!-- Navbar-->
      <header class="main-header hidden-print"><a class="logo" href="index.html">质量安全</a>
        <nav class="navbar navbar-static-top">
          <!-- Sidebar toggle button--><a class="sidebar-toggle" href="#" data-toggle="offcanvas"></a>
          <!-- Navbar Right Menu-->
          <div class="navbar-custom-menu">
            <ul class="top-nav">
              <!-- User Menu-->
              <li class="dropdown">
              	<a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              		<i class="fa fa-user fa-lg"></i>
             	</a>
                <ul class="dropdown-menu settings-menu">
                  <li><a href="${ctx}/logout"><i class="fa fa-sign-out fa-lg"></i> 退出</a></li>
                </ul>
              </li>
            </ul>
          </div>
        </nav>
      </header>
      
      <!-- Side-Nav-->
      <aside class="main-sidebar hidden-print">
        <section class="sidebar">
          <div class="user-panel">
            <div class="pull-left image"><img class="img-circle" src="${fns:getUser().photo}" alt="User Image"></div>
            <div class="pull-left info">
              <p>${fns:getUser().name}</p>
            </div>
          </div>
          <!-- Sidebar Menu-->
          <ul class="sidebar-menu">
            <li class="active"><a href="${ctx}/mobile/index"><i class="fa fa-dashboard"></i><span>项目统计</span></a></li>
          </ul>
          <ul class="sidebar-menu">
            <li><a href="${ctx}/mobile/quality/list"><i class="fa fa-dashboard"></i><span>质量隐患</span></a></li>
          </ul>
        </section>
      </aside>
      <div class="content-wrapper">
        <div class="page-title">
          <div>
            <ul class="breadcrumb">
              <li><i class="fa fa-home fa-lg"></i></li>
              <li><a href="${ctx}/mobile/index">项目统计</a></li>
            </ul>
          </div>
        </div>
        
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
          
          <div class="clearfix"></div>
          <div class="col-md-6">
            <div class="card">
              <h4 class="card-title" style="margin-left: 35px;">高速公路基本信息一览表汇总</h4>
              	<div class="tableCon">  
    				<div class="tableScr">  
              			<table class="table table-bordered">
			                <tbody>
			                  <tr>
			                    <td>项目名称</td>
								<td>项目类型</td>
								<td>批准工期(月)</td>
								<td>开工时间</td>
								<td>设计时速(km/h)</td>
								<td>建设里程(km)</td>
								<td>施工合同段(个)</td>
								<td>监理合同段(个)</td>
								<td>桥梁累计长度(m)</td>
								<td>隧道累计长度(m)</td>
			                  </tr>
			                  <c:if test="${not empty projectList }">
								<c:forEach items="${projectList}" var="projectSimpleInfo" varStatus="status">
									<tr>
										<td> ${projectSimpleInfo.projectSimpleName} </td>
										<td> ${fns:getDictLabel(projectSimpleInfo.projectType, 'project_simple_type', '')}</td>
										<td> ${projectSimpleInfo.approveDuration} </td>
										<td> <fmt:formatDate value="${projectSimpleInfo.proposedStartTime}" pattern="yyyy-MM-dd"/></td>
										<td> ${projectSimpleInfo.designSpeed} </td>
										<td> ${projectSimpleInfo.buildMileage} </td>
										<td> ${projectSimpleInfo.proSimpleSupervision.contractSectionNum} </td>
										<td> ${projectSimpleInfo.proSimpleSupervision.supervisionSectionNum} </td>
										<td> ${projectSimpleInfo.proSimpleBridgeTunnel.bridgeTotalLength} </td>
										<td> ${projectSimpleInfo.proSimpleBridgeTunnel.tunnelTotalLength} </td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty projectList }">
								<tr><td  style="text-align: center" colspan="10">没有数据</td></tr>
							</c:if>
			                </tbody>
              			</table>
            		</div>
          		</div>
          	</div>
      	</div>
      	
      	
      	
      	<div class="clearfix"></div>
          <div class="col-md-6">
            <div class="card">
              <h4 class="card-title" style="margin-left: 35px;">项目安全生产汇总一览表</h4>
              	<div class="tableCon">  
    				<div class="tableScr">  
              			<table class="table table-bordered">
			                <tbody>
			                  <tr>
			                    <td>项目名称</td>           
								<td>安全生产管理人员数</td>      
								<td>特种设备数</td>          
								<td>专用设备数</td>          
								<td>特种作业人员数</td>        
								<td>特种设备人员数</td>        
			                  </tr>
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
          	</div>
      	</div>
      	
      	
      	<div class="clearfix"></div>
          <div class="col-md-6">
            <div class="card">
              <h4 class="card-title" style="margin-left: 35px;">项目各单位信息汇总一览表</h4>
              	<div class="tableCon">  
    				<div class="tableScr">  
              			<table class="table table-bordered">
			                <tbody>
			                  <tr>
			                    <td>项目名称</td>           
								<td>特殊路基个数 </td>        
								<td>建设单位个数</td>         
								<td>施工单位个数</td>         
								<td>监理单位个数</td>         
								<td>中心试验室个数</td>        
								<td>工地试验室个数</td>        
								<td>第三方检测单位个数</td>      
			                  </tr>
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
          	</div>
      	</div>
      	
      	
      	
        </div>
      </div>
    </div>
    <!-- Javascripts-->
    <script src="${ctxStatic}/font/js/jquery-2.1.4.min.js"></script>
    <script src="${ctxStatic}/font/js/bootstrap.min.js"></script>
    <script src="${ctxStatic}/font/js/plugins/pace.min.js"></script>
    <script src="${ctxStatic}/font/js/main.js"></script>
    <script type="text/javascript" src="${ctxStatic}/font/js/plugins/chart.js"></script>
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