<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>特种、专用设备操作人员信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
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
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/safe/safeOpertionUserinfo/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/safe/safeOpertionUserinfo/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/safe/safeOpertionUserinfo/">特种、专用设备操作人员信息列表</a></li>
		<shiro:hasPermission name="safe:safeOpertionUserinfo:edit">
			<li><a href="${ctx}/safe/safeOpertionUserinfo/form">特种、专用设备操作人员信息添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="safeOpertionUserinfo" action="${ctx}/safe/safeOpertionUserinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>姓名：</label> 
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li><label style="width: 91px;">执业证书编号：</label> 
				<form:input path="certificateNo" htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">身份证号</th>
				<th style="text-align: center">性别</th>
				<th style="text-align: center">执业证书编号</th>
				<th style="text-align: center">证书有效期</th>
				<th style="text-align: center">作业工点或位置</th>
				<th style="text-align: center">特种、专用设备操作人员类型</th>
				<th style="text-align: center">备注</th>
				<shiro:hasPermission name="safe:safeOpertionUserinfo:edit">
					<th style="text-align: center">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="safeOpertionUserinfo">
				<tr>
					<td><a
						href="${ctx}/safe/safeOpertionUserinfo/form?id=${safeOpertionUserinfo.id}">
							${safeOpertionUserinfo.name} </a></td>
					<td style="text-align: center">${safeOpertionUserinfo.cid}</td>
					<td style="text-align: center">${fns:getDictLabel(safeOpertionUserinfo.gender, 'sex', '')}
					</td>
					<td style="text-align: center">${safeOpertionUserinfo.certificateNo}</td>
					<td style="text-align: center"><fmt:formatDate
							value="${safeOpertionUserinfo.certificateValid}"
							pattern="yyyy-MM-dd" /></td>
					<td>${safeOpertionUserinfo.workSite}</td>
					<td style="text-align: center">${fns:getDictLabel(safeOpertionUserinfo.opertionUserType, 'opertion_user_type', '')}
					</td>
					<td>${safeOpertionUserinfo.remarks}</td>
					<shiro:hasPermission name="safe:safeOpertionUserinfo:edit">
						<td style="text-align: center"><a
							href="${ctx}/safe/safeOpertionUserinfo/form?id=${safeOpertionUserinfo.id}" title="编辑"><i class="icon-edit"></i></a>
							<a
							href="${ctx}/safe/safeOpertionUserinfo/delete?id=${safeOpertionUserinfo.id}"
							onclick="return confirmx('确认要删除该特种、专用设备操作人员信息吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
						</td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>