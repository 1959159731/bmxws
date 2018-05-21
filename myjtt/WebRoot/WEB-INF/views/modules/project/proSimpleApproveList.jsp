<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目基本信息-审批信息管理</title>
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
		<li class="active"><a href="${ctx}/project/proSimpleApprove/">项目基本信息-审批信息列表</a></li>
		<shiro:hasPermission name="project:proSimpleApprove:edit"><li><a href="${ctx}/project/proSimpleApprove/form">项目基本信息-审批信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="proSimpleApprove" action="${ctx}/project/proSimpleApprove/" method="post" class="breadcrumb form-search">
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
				<th style="text-align: center">项目ID</th>
				<th style="text-align: center">项目立项审批单位</th>
				<th style="text-align: center">项目立项批准文号</th>
				<th style="text-align: center">项目立项批准时间</th>
				<th style="text-align: center">工可批复审批单位</th>
				<th style="text-align: center">工可批复批准文号</th>
				<th style="text-align: center">工可批复批准时间</th>
				<th style="text-align: center">初步设计审查审批单位</th>
				<th style="text-align: center">初步设计审查批准文号</th>
				<th style="text-align: center">初步设计审查批准时间</th>
				<th style="text-align: center">施工图设计批复审批单位</th>
				<th style="text-align: center">施工图设计批复批准文号</th>
				<th style="text-align: center">施工图设计批复批准时间</th>
				<shiro:hasPermission name="project:proSimpleApprove:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="proSimpleApprove">
			<tr>
				<td><a href="${ctx}/project/proSimpleApprove/form?id=${proSimpleApprove.id}">
					${proSimpleApprove.projectId}
				</a></td>
				<td>
					${proSimpleApprove.approvalCompany}
				</td>
				<td>
					${proSimpleApprove.approvalNum}
				</td>
				<td>
					<fmt:formatDate value="${proSimpleApprove.approvalTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${proSimpleApprove.workersApprovalCompany}
				</td>
				<td>
					${proSimpleApprove.workersApprovalNum}
				</td>
				<td>
					<fmt:formatDate value="${proSimpleApprove.workersApprovalTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${proSimpleApprove.designApprovalCompany}
				</td>
				<td>
					${proSimpleApprove.designApprovalNum}
				</td>
				<td>
					<fmt:formatDate value="${proSimpleApprove.designApprovalTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${proSimpleApprove.constructionDesignApprovalUnit}
				</td>
				<td>
					${proSimpleApprove.constructionDesignApprovalNum}
				</td>
				<td>
					<fmt:formatDate value="${proSimpleApprove.constructionDesignApprovalTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="project:proSimpleApprove:edit"><td>
    				<a href="${ctx}/project/proSimpleApprove/form?id=${proSimpleApprove.id}">修改</a>
					<a href="${ctx}/project/proSimpleApprove/delete?id=${proSimpleApprove.id}" onclick="return confirmx('确认要删除该项目基本信息-审批信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>