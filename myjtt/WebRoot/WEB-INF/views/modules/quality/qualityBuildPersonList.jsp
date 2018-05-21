<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>建设单位管理人员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityBuildPerson/");
				$("#searchForm").submit();
			});
			
			
			$("#exportSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityBuildPerson/export");
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
		<form id="importForm" action="${ctx}/quality/qualityBuildPerson/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/quality/qualityBuildPerson/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/quality/qualityBuildPerson/">建设单位管理人员列表</a></li>
		<shiro:hasPermission name="quality:qualityBuildPerson:edit"><li><a href="${ctx}/quality/qualityBuildPerson/form">建设单位管理人员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualityBuildPerson" action="" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
				<form:select path="proSimpleInfo.id" class="input-medium">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${projectList}" var="item">
						<form:option value="${item.id}" label="${item.projectSimpleName }"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">项目名称</th>
				<th style="text-align: center">建设单位名称</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">身份证</th>
				<th style="text-align: center">部门</th>
				<th style="text-align: center">岗位</th>
				<th style="text-align: center">职称</th>
				<th style="text-align: center">备注</th>
				<shiro:hasPermission name="quality:qualityBuildPerson:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualityBuildPerson">
			<tr>
				<td><a href="${ctx}/quality/qualityBuildPerson/form?id=${qualityBuildPerson.id}">
					${qualityBuildPerson.proSimpleInfo.projectSimpleName}
				</a></td>
				<td>
					${qualityBuildPerson.buildCompany}
				</td>
				<td>
					${qualityBuildPerson.name}
				</td>
				<td style="text-align: center">
					${qualityBuildPerson.idcard}
				</td>
				<td>
					${qualityBuildPerson.department}
				</td>
				<td>
					${qualityBuildPerson.post}
				</td>
				<td>
					${qualityBuildPerson.jobTitle}
				</td>
				<td>
					${qualityBuildPerson.remarks}
				</td>
				<shiro:hasPermission name="quality:qualityBuildPerson:edit"><td style="text-align: center">
    				<a href="${ctx}/quality/qualityBuildPerson/form?id=${qualityBuildPerson.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualityBuildPerson/delete?id=${qualityBuildPerson.id}" onclick="return confirmx('确认要删除该建设单位管理人员吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>