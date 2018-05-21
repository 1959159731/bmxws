<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>施工单位信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityConstructionInfo/");
				$("#searchForm").submit();
			});
			
			
			$("#exportSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityConstructionInfo/export");
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
		<form id="importForm" action="${ctx}/quality/qualityConstructionInfo/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/quality/qualityConstructionInfo/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/quality/qualityConstructionInfo/">施工单位信息列表</a></li>
		<shiro:hasPermission name="quality:qualityConstructionInfo:edit"><li><a href="${ctx}/quality/qualityConstructionInfo/form">施工单位信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualityConstructionInfo" action="${ctx}/quality/qualityConstructionInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目：</label>
				<form:select id="projectSelect" path="proSimpleInfo.id" class="input-medium">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${projectList}" var="item">
						<form:option value="${item.id}" label="${item.projectSimpleName }"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>合同段编号：</label>
				<form:input path="contractSectionLabel" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>施工单位：</label>
				<form:input path="constructionName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2" style="text-align: center">项目</th>
				<th rowspan="2" style="text-align: center">合同段编号</th>
				<th rowspan="2" style="text-align: center">施工单位</th>
				<th rowspan="2" style="text-align: center">起讫桩号</th>
				<th rowspan="2" style="text-align: center">合同价(万元)</th>
				<th rowspan="2" style="text-align: center">主要工程数量</th>
				<th colspan="7" style="text-align: center">主要人员姓名</th>
				<shiro:hasPermission name="quality:qualityConstructionInfo:edit"><th rowspan="2" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
			<tr>
				<th style="text-align: center">项目经理</th>
				<th style="text-align: center">项目总工</th>
				<th style="text-align: center">工程部长</th>
				<th style="text-align: center">质检部长</th>
				<th style="text-align: center">质量总监</th>
				<th style="text-align: center">安全总监</th>
				<th style="text-align: center">试验室主任</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualityConstructionInfo">
			<tr>
				<td><a href="${ctx}/quality/qualityConstructionInfo/form?id=${qualityConstructionInfo.id}">
					${qualityConstructionInfo.proSimpleInfo.projectSimpleName }
				</a></td>
				<td>
					${qualityConstructionInfo.contractSectionLabel}
				</td>
				<td>
					${qualityConstructionInfo.constructionName}
				</td>
				<td>
					${qualityConstructionInfo.startingStation } - ${qualityConstructionInfo.endingStation}
				</td>
				<td style="text-align: right">
					${qualityConstructionInfo.contractPrice}
				</td>
				<td style="text-align: right">
					${qualityConstructionInfo.majorProNum}
				</td>
				<td>
					${qualityConstructionInfo.projectManager}
				</td>
				<td>
					${qualityConstructionInfo.projectChiefEngineer}
				</td>
				<td>
					${qualityConstructionInfo.engineeringMinister}
				</td>
				<td>
					${qualityConstructionInfo.qc}
				</td>
				<td>
					${qualityConstructionInfo.qualitySupervisor}
				</td>
				<td>
					${qualityConstructionInfo.safetyDirector}
				</td>
				<td>
					${qualityConstructionInfo.testDirector}
				</td>
				<shiro:hasPermission name="quality:qualityConstructionInfo:edit"><td style="text-align: center">
    				<a href="${ctx}/quality/qualityConstructionInfo/form?id=${qualityConstructionInfo.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualityConstructionInfo/delete?id=${qualityConstructionInfo.id}" onclick="return confirmx('确认要删除该施工单位信息吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>