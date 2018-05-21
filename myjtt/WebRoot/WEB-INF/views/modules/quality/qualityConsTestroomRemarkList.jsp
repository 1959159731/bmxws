<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>施工单位试验室备案信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityConsTestroomRemark/");
				$("#searchForm").submit();
			});
			
			
			$("#exportSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityConsTestroomRemark/export");
				$("#searchForm").submit();
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/quality/qualityConsTestroomRemark/">施工单位试验室备案信息列表</a></li>
		<shiro:hasPermission name="quality:qualityConsTestroomRemark:edit"><li><a href="${ctx}/quality/qualityConsTestroomRemark/form">施工单位试验室备案信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualityConsTestroomRemark" action="${ctx}/quality/qualityConsTestroomRemark/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目：</label>
				<form:select path="proSimpleInfo.id" class="input-xlarge required">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${projectList}" var="var">
						<form:option value="${var.id }" label="${var.projectSimpleName }"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="submit" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">项目</th>
				<th style="text-align: center">合同段</th>
				<th style="text-align: center">母体试验室</th>
				<th style="text-align: center">资格证书编号</th>
				<th style="text-align: center">授权负责人</th>
				<shiro:hasPermission name="quality:qualityConsTestroomRemark:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualityConsTestroomRemark">
			<tr>
				<td><a href="${ctx}/quality/qualityConsTestroomRemark/form?id=${qualityConsTestroomRemark.id}">
					${qualityConsTestroomRemark.proSimpleInfo.projectSimpleName}
				</a></td>
				<td>
					${qualityConsTestroomRemark.contractSectionLabel}
				</td>
				<td>
					${qualityConsTestroomRemark.maternalLaboratory}
				</td>
				<td>
					${qualityConsTestroomRemark.qacertificateNum}
				</td>
				<td>
					${qualityConsTestroomRemark.authorizedPerson}
				</td>
				<shiro:hasPermission name="quality:qualityConsTestroomRemark:edit"><td style="text-align: center">
    				<a href="${ctx}/quality/qualityConsTestroomRemark/form?id=${qualityConsTestroomRemark.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualityConsTestroomRemark/delete?id=${qualityConsTestroomRemark.id}" onclick="return confirmx('确认要删除该中心试验室备案信息吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>