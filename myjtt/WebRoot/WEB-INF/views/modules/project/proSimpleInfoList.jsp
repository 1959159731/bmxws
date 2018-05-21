<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目基本信息管理</title>
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
		<li class="active"><a href="${ctx}/project/proSimpleInfo/">项目基本信息列表</a></li>
		<shiro:hasPermission name="project:proSimpleInfo:edit"><li><a href="${ctx}/project/proSimpleInfo/form">项目基本信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="proSimpleInfo" action="${ctx}/project/proSimpleInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>项目类型：</label>
				<form:select path="projectType" class="input-medium" style="width: 177px;">
					<form:option value="" label="---请选择---"/>
					<form:options items="${fns:getDictList('project_simple_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="clearfix"></li>
		</ul>
		<ul class="ul-form">
			<li><label>开工时间：</label>
				<input name="proposedStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${proSimpleInfo.proposedStartTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>交工时间：</label>
				<input name="proposedDeliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${proSimpleInfo.proposedDeliveryTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">项目名称</th>
				<th style="text-align: center">简称</th>
				<th style="text-align: center">项目类型</th>
				<th style="text-align: center">项目法人</th>
				<th style="text-align: center">现场管理机构</th>
				<th style="text-align: center">设计单位</th>
				<th style="text-align: center">批准工期（月）</th>
				<th style="text-align: center">合同工期（月）</th>
				<th style="text-align: center">（拟）开工时间</th>
				<th style="text-align: center">（拟）交工时间</th>
				<th style="text-align: center">导出</th>
				<shiro:hasPermission name="project:proSimpleInfo:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="proSimpleInfo">
			<tr>
				<td><a href="${ctx}/project/proSimpleInfo/form?id=${proSimpleInfo.id}">
					${proSimpleInfo.projectName}
				</a></td>
				<td>
					${proSimpleInfo.projectSimpleName}
				</td>
				<td>
					${fns:getDictLabel(proSimpleInfo.projectType, 'project_simple_type', '')}
				</td>
				<td>
					${proSimpleInfo.projectLegal}
				</td>
				<td>
					${proSimpleInfo.siteManagementAgencies}
				</td>
				<td>
					${proSimpleInfo.designUnit}
				</td>
				<td style="text-align: right">
					${proSimpleInfo.approveDuration}
				</td>
				<td style="text-align: right">
					${proSimpleInfo.contractDuration}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${proSimpleInfo.proposedStartTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${proSimpleInfo.proposedDeliveryTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
					<a href="${ctx}/project/proSimpleInfo/export?id=${proSimpleInfo.id}" title="导出"><i class="icon-download-alt"></i></a>
				</td>
				<shiro:hasPermission name="project:proSimpleInfo:edit">
					<td style="text-align: center">
    				<a href="${ctx}/project/proSimpleInfo/form?id=${proSimpleInfo.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/project/proSimpleInfo/delete?id=${proSimpleInfo.id}" onclick="return confirmx('确认要删除该项目基本信息吗？', this.href)" 
					title="删除">
					<i class="icon-remove"></i></a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>