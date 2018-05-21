<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目施工、监理单位信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
		<li class="active"><a href="${ctx}/project/proSimpleSupervision/">项目施工、监理单位信息列表</a></li>
		<shiro:hasPermission name="project:proSimpleSupervision:edit"><li><a href="${ctx}/project/proSimpleSupervision/form">项目施工、监理单位信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="proSimpleSupervision" action="${ctx}/project/proSimpleSupervision/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">project_id</th>
				<th style="text-align: center">contract_section_num</th>
				<th style="text-align: center">supervision_mode</th>
				<th style="text-align: center">supervision_section_num</th>
				<th style="text-align: center">project_contact_username</th>
				<th style="text-align: center">project_contact_phone</th>
				<shiro:hasPermission name="project:proSimpleSupervision:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="proSimpleSupervision">
			<tr>
				<td><a href="${ctx}/project/proSimpleSupervision/form?id=${proSimpleSupervision.id}">
					${proSimpleSupervision.projectId}
				</a></td>
				<td>
					${proSimpleSupervision.contractSectionNum}
				</td>
				<td>
					${proSimpleSupervision.supervisionMode}
				</td>
				<td>
					${proSimpleSupervision.supervisionSectionNum}
				</td>
				<td>
					${proSimpleSupervision.projectContactUsername}
				</td>
				<td>
					${proSimpleSupervision.projectContactPhone}
				</td>
				<shiro:hasPermission name="project:proSimpleSupervision:edit"><td>
    				<a href="${ctx}/project/proSimpleSupervision/form?id=${proSimpleSupervision.id}">修改</a>
					<a href="${ctx}/project/proSimpleSupervision/delete?id=${proSimpleSupervision.id}" onclick="return confirmx('确认要删除该项目施工、监理单位信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>