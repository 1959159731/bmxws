<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目桥梁、隧道信息管理</title>
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
		<li class="active"><a href="${ctx}/project/proSimpleBridgeTunnel/">项目桥梁、隧道信息列表</a></li>
		<shiro:hasPermission name="project:proSimpleBridgeTunnel:edit"><li><a href="${ctx}/project/proSimpleBridgeTunnel/form">项目桥梁、隧道信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="proSimpleBridgeTunnel" action="${ctx}/project/proSimpleBridgeTunnel/" method="post" class="breadcrumb form-search">
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
				<th style="text-align: center">特大桥数量（座）</th>
				<shiro:hasPermission name="project:proSimpleBridgeTunnel:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="proSimpleBridgeTunnel">
			<tr>
				<td><a href="${ctx}/project/proSimpleBridgeTunnel/form?id=${proSimpleBridgeTunnel.id}">
					${proSimpleBridgeTunnel.projectId}
				</a></td>
				<td>
					${proSimpleBridgeTunnel.extraLargeBridgeNum}
				</td>
				<shiro:hasPermission name="project:proSimpleBridgeTunnel:edit"><td>
    				<a href="${ctx}/project/proSimpleBridgeTunnel/form?id=${proSimpleBridgeTunnel.id}">修改</a>
					<a href="${ctx}/project/proSimpleBridgeTunnel/delete?id=${proSimpleBridgeTunnel.id}" onclick="return confirmx('确认要删除该项目桥梁、隧道信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>