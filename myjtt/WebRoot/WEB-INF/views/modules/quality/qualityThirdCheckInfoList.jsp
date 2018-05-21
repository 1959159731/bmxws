<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>第三方检测单位信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityThirdCheckInfo/");
				$("#searchForm").submit();
			});
			
			
			$("#exportSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityThirdCheckInfo/export");
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
		<form id="importForm" action="${ctx}/quality/qualityThirdCheckInfo/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/quality/qualityThirdCheckInfo/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/quality/qualityThirdCheckInfo/">第三方检测单位信息列表</a></li>
		<shiro:hasPermission name="quality:qualityThirdCheckInfo:edit"><li><a href="${ctx}/quality/qualityThirdCheckInfo/form">第三方检测单位信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualityThirdCheckInfo" action="${ctx}/quality/qualityThirdCheckInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
				<form:select path="proSimpleInfo.id" class="input-xlarge required">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${projectList}" var="var">
						<form:option value="${var.id }" label="${var.projectSimpleName }"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="submit" value="导出"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">项目名称</th>
				<th style="text-align: center">合同段编号</th>
				<th style="text-align: center">检测单位</th>
				<th style="text-align: center">所检测合同段</th>
				<th style="text-align: center">检测单位负责人（联系电话）</th>
				<th style="text-align: center">现场检查负责人（联系电话）</th>
				<th style="text-align: center">备注</th>
				<shiro:hasPermission name="quality:qualityThirdCheckInfo:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualityThirdCheckInfo">
			<tr>
				<td><a href="${ctx}/quality/qualityThirdCheckInfo/form?id=${qualityThirdCheckInfo.id}">
					${qualityThirdCheckInfo.proSimpleInfo.projectSimpleName}
				</a></td>
				<td>
					${qualityThirdCheckInfo.contractSectionLabel}
				</td>
				<td>
					${qualityThirdCheckInfo.detectionUnit}
				</td>
				<td>
					${qualityThirdCheckInfo.indetectionContractSectionLabel}
				</td>
				<td style="text-align: center">
					${qualityThirdCheckInfo.inspectionLeader} - ${qualityThirdCheckInfo.inspectionLeaderPhonenum}
				</td>
				<td style="text-align: center">
					${qualityThirdCheckInfo.onsiteInspectionLeader} - ${qualityThirdCheckInfo.onsiteInspectionLeaderPhonenum}
				</td>
				<td>
					${qualityThirdCheckInfo.remarks }
				</td>
				<shiro:hasPermission name="quality:qualityThirdCheckInfo:edit"><td style="text-align: center">
    				<a href="${ctx}/quality/qualityThirdCheckInfo/form?id=${qualityThirdCheckInfo.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualityThirdCheckInfo/delete?id=${qualityThirdCheckInfo.id}" onclick="return confirmx('确认要删除该第三方检测单位信息吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>