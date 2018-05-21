<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监理单位信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualitySupervisonInfo/");
				$("#searchForm").submit();
			});
			
			
			$("#exportSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualitySupervisonInfo/export");
				$("#searchForm").submit();
			});
			
			$("#btnImport").click(function() {
				$.jBox($("#importBox").html(), {
					title : "导入数据",
					buttons : {
						"关闭" : true
					},
					bottomText : "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"
				});
			});
			
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/quality/qualitySupervisonInfo/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/quality/qualitySupervisonInfo/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/quality/qualitySupervisonInfo/">监理单位信息列表</a></li>
		<shiro:hasPermission name="quality:qualitySupervisonInfo:edit"><li><a href="${ctx}/quality/qualitySupervisonInfo/form">监理单位信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualitySupervisonInfo" action="" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: 120px;">项目名称：</label>
				<form:select id="projectSelect" path="proSimpleInfo.id" class="input-medium" style="width: 177px;">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${projectList}" var="item">
						<form:option value="${item.id}" label="${item.projectSimpleName }"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="submit" value="导出"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			<li class="clearfix"></li>
		</ul>
		<ul class="ul-form">
			<li><label style="width: 120px;">监理合同段编号：</label>
				<form:input path="contractSectionLabel" htmlEscape="false" maxlength="64" class="input-medium" />
			</li>
			<li><label style="width: 120px;">监理单位名称：</label>
				<form:input path="supervisionName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">项目</th>
				<th style="text-align: center">监理合同段编号</th>
				<th style="text-align: center">监理单位名称</th>
				<th style="text-align: center">所管辖合施工同段编号</th>
				<th style="text-align: center">起讫桩号</th>
				<th style="text-align: center">监理服务费(万元)</th>
				<th style="text-align: center">监理工程造价（万元）</th>
				<th style="text-align: center">监理合同开始时间、结束时间</th>
				<th style="text-align: center">备注</th>
				<shiro:hasPermission name="quality:qualitySupervisonInfo:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualitySupervisonInfo">
			<tr>
				<td><a href="${ctx}/quality/qualitySupervisonInfo/form?id=${qualitySupervisonInfo.id}">
					${qualitySupervisonInfo.proSimpleInfo.projectSimpleName }
				</a></td>
				<td>
					${qualitySupervisonInfo.contractSectionLabel}
				</td>
				<td>
					${qualitySupervisonInfo.supervisionName}
				</td>
				<td>
					${qualitySupervisonInfo.haveContractSectionLabel}
				</td>
				<td>
					${qualitySupervisonInfo.startingStation} - ${qualitySupervisonInfo.endingStation}
				</td>
				<td style="text-align: right">
					${qualitySupervisonInfo.supervisionServiceFee}
				</td>
				<td style="text-align: right">
					${qualitySupervisonInfo.supervisionProjectCosts}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${qualitySupervisonInfo.supervisionContractStart}" pattern="yyyy-MM-dd"/>
					<c:if test="${not empty qualitySupervisonInfo.supervisionContractEnd }">
						- <fmt:formatDate value="${qualitySupervisonInfo.supervisionContractEnd}" pattern="yyyy-MM-dd"/>
					</c:if>
				</td>
				<td>
					${qualitySupervisonInfo.remarks}
				</td>
				<shiro:hasPermission name="quality:qualitySupervisonInfo:edit"><td style="text-align: center">
    				<a href="${ctx}/quality/qualitySupervisonInfo/form?id=${qualitySupervisonInfo.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualitySupervisonInfo/delete?id=${qualitySupervisonInfo.id}" onclick="return confirmx('确认要删除该监理单位信息吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>