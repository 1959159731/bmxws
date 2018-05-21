<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>质量检测统计数据管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#dateForYear").val("");
	});
	
	function pickedFunc(){  
        $("#dateForYear").val( $(this).val() );
	};
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx }/quality/qualityDataIndex/">质量检测统计数据列表</a></li>
		<shiro:hasPermission name="quality:qualityDataIndex:edit">
			<li><a href="${ctx }/quality/qualityDataIndex/form">质量检测统计数据添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualityDataIndex" action="${ctx}/quality/qualityDataIndex/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>项目名称：</label> <form:select id="project_id" path="proSimpleInfo.id" class="input-medium">
					<form:option value="" label="---请选择---" />
					<c:forEach items="${projectList }" var="item">
						<form:option value="${item.id }" label="${item.projectSimpleName  }" />
					</c:forEach>
				</form:select></li>
			<li><label>项目标段：</label> 
				<form:input path="projectLabel" htmlEscape="false" maxlength="11" class="input-large required" /></li>
			<li><label>检查时间：</label>
				<form:hidden path="checkDate" id="dateForYear" class="Wdate"/>
				<input name="checkDateForYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width: 60px;"
					value="<fmt:formatDate value="${qualityDataIndex.checkDateForYear}" pattern="yyyy"/>"
					onclick="WdatePicker({dateFmt:'yyyy', isShowClear:false, onpicked:pickedFunc});"/>年&nbsp;&nbsp;&nbsp;
				<form:select path="checkDateSeason" class="input-small required" style="width: 100px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_date_season')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select></li>
			<li class="btns">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message }" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">项目名称</th>
				<th style="text-align: center">标段</th>
				<th style="text-align: center">检查时间</th>
				<!-- <th style="text-align: center">身份证</th>
				<th style="text-align: center">部门</th>
				<th style="text-align: center">岗位</th>
				<th style="text-align: center">职称</th>
				<th style="text-align: center">备注</th> -->
				<shiro:hasPermission name="quality:qualityDataIndex:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualityDataIndex">
			<tr>
				<td><a href="${ctx}/quality/qualityDataIndex/see?id=${qualityDataIndex.id}" title="查看">
					${qualityDataIndex.proSimpleInfo.projectSimpleName}
				</a></td>
				<td>
					${qualityDataIndex.projectLabel}
				</td>
				<td>
					${qualityDataIndex.checkDate }年&nbsp;${fns:getDictLabel(qualityDataIndex.checkDateSeason, 'check_date_season', '')}
				</td>
				<%-- <td style="text-align: center">
					${qualityDataIndex.idcard}
				</td>
				<td>
					${qualityDataIndex.department}
				</td>
				<td>
					${qualityDataIndex.post}
				</td>
				<td>
					${qualityDataIndex.jobTitle}
				</td>
				<td>
					${qualityDataIndex.remarks}
				</td> --%>
				<shiro:hasPermission name="quality:qualityDataIndex:edit"><td style="text-align: center">
					<a href="${ctx}/quality/qualityDataIndex/export?id=${qualityDataIndex.id}" title="导出"><i class="icon-download-alt"></i></a> 
    				<a href="${ctx}/quality/qualityDataIndex/form?id=${qualityDataIndex.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualityDataIndex/delete?id=${qualityDataIndex.id}" onclick="return confirmx('确认要删除该统计数据吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>