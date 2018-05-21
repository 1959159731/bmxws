<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>质量隐患管理</title>
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
		<li class="active"><a href="${ctx}/ssp/qualitySafe/">质量隐患列表</a></li>
		<shiro:hasPermission name="ssp:qualitySafe:edit"><li><a href="${ctx}/ssp/qualitySafe/form">质量隐患添加</a></li></shiro:hasPermission>		
	</ul>
	<form:form id="searchForm" modelAttribute="qualitySafe" action="${ctx}/ssp/qualitySafe/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称简称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>		
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">隐患简称</th>
				<th style="text-align: center">合同段编号</th>
				<th style="text-align: center">隐患信息标题名称</th>
				<th style="text-align: center">隐患信息描述</th>
				<th style="text-align: center">照片</th>
				<shiro:hasPermission name="ssp:qualitySafe:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualitySafe">
			<tr>
			
				<td>
					<a href="${ctx}/ssp/qualitySafe/form?id=${qualitySafe.id}"></a>
					<!--<c:out value="${qualitySafe.projectSimpleName}"/>-->
					${qualitySafe.projectSimpleName}
					
				</td>
				<td>
					<!--<c:out value="${qualitySafe.contractSectionLabel}"/>-->
					${qualitySafe.contractSectionLabel}
				</td>
				<td>
					<!--<c:out value="${qualitySafe.dangerousTitle}"/>-->
					${qualitySafe.dangerousTitle}
				</td>
				<td>
					
					<!--<c:out value="${qualitySafe.safeMessage}"/>-->
					${qualitySafe.safeMessage}
				</td>
				<td style="text-align: right">
					<!--<c:out value="${qualitySafe.photo}"/>-->
					${qualitySafe.photo}
				</td>
				
				<shiro:hasPermission name="ssp:qualitySafe:edit">
				<td style="text-align: center">
    				<a href="${ctx}/ssp/qualitySafe/form?id=${qualitySafe.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/ssp/qualitySafe/delete?id=${qualitySafe.id}" onclick="return confirmx('确认要删除该质量隐患吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>