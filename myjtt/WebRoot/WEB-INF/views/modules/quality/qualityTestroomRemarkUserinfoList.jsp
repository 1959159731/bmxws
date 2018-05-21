<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试验室人员备案信息管理</title>
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
		<li class="active"><a href="${ctx}/quality/qualityTestroomRemarkUserinfo/">试验室人员备案信息列表</a></li>
		<shiro:hasPermission name="quality:qualityTestroomRemarkUserinfo:edit"><li><a href="${ctx}/quality/qualityTestroomRemarkUserinfo/form">试验室人员备案信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualityTestroomRemarkUserinfo" action="${ctx}/quality/qualityTestroomRemarkUserinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>人员类型（1-中心试验室 2-施工单位试验室）：</label>
				<form:select path="remarkUsertype" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('quality_testroom_usertype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>备案人员姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">关联试验室备案信息表字段id</th>
				<th style="text-align: center">人员类型（1-中心试验室 2-施工单位试验室）</th>
				<th style="text-align: center">备案人员姓名</th>
				<th style="text-align: center">从业资格</th>
				<th style="text-align: center">资格证书编号</th>
				<th style="text-align: center">备注信息</th>
				<shiro:hasPermission name="quality:qualityTestroomRemarkUserinfo:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualityTestroomRemarkUserinfo">
			<tr>
				<td><a href="${ctx}/quality/qualityTestroomRemarkUserinfo/form?id=${qualityTestroomRemarkUserinfo.id}">
					${qualityTestroomRemarkUserinfo.remarkId}
				</a></td>
				<td>
					${fns:getDictLabel(qualityTestroomRemarkUserinfo.remarkUsertype, 'quality_testroom_usertype', '')}
				</td>
				<td>
					${qualityTestroomRemarkUserinfo.name}
				</td>
				<td>
					${qualityTestroomRemarkUserinfo.qualifications}
				</td>
				<td>
					${qualityTestroomRemarkUserinfo.qualificationCertNum}
				</td>
				<td>
					${qualityTestroomRemarkUserinfo.remarks}
				</td>
				<shiro:hasPermission name="quality:qualityTestroomRemarkUserinfo:edit"><td style="text-align: center">
    				<a href="${ctx}/quality/qualityTestroomRemarkUserinfo/form?id=${qualityTestroomRemarkUserinfo.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualityTestroomRemarkUserinfo/delete?id=${qualityTestroomRemarkUserinfo.id}" onclick="return confirmx('确认要删除该试验室人员备案信息吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>