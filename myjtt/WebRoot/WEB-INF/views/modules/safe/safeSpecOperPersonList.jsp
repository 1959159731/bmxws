<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>特种作业人员管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready( function() {
		
		$("#projectNameSelect").on("change", function() {
			var _projectId = $(this).val();
			if ( _projectId ) {
				$.ajax({
					type: "POST",
					url: "${ctx}/safe/safeSpecOperPerson/getContractLabel",
					data: {
						projectId : _projectId
					},
					success : function(data) {
						$("#contractSectionLabelSelect").empty();
						$("<option value=''>---请选择---</option>").appendTo($("#contractSectionLabelSelect"));
						$.each(data,function(index,item) {
							$("<option value='" + item + "'>" + item + "</option>").appendTo( $("#contractSectionLabelSelect"));
						});
						$("#contractSectionLabelSelect").change();
					}
				});
			} else {
				$("#contractSectionLabelSelect").empty();
				$("<option value=''>---请选择---</option>").appendTo($("#contractSectionLabelSelect"));
				$("#contractSectionLabelSelect").change();
			}
		});

		
		$("#btnSubmit").on("click", function(){
			$("#searchForm").attr("action", "${ctx}/safe/safeSpecOperPerson/");
			$("#searchForm").submit();
		});
		
		
		$("#exportSubmit").on("click", function(){
			$("#searchForm").attr("action", "${ctx}/safe/safeSpecOperPerson/export");
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
		<form id="importForm" action="${ctx}/safe/safeSpecOperPerson/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/safe/safeSpecOperPerson/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/safe/safeSpecOperPerson/">特种作业人员列表</a></li>
		<shiro:hasPermission name="safe:safeSpecOperPerson:edit">
			<li><a href="${ctx}/safe/safeSpecOperPerson/form">特种作业人员添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="safeSpecOperPerson"
		action="${ctx}/safe/safeSpecOperPerson/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>项目名称：</label> 
				<form:select id="projectNameSelect" path="proSimpleInfo.id" class="input-medium">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${projectNameList}" var="var">
						<form:option value="${var.id}" label="${var.projectSimpleName }"/>
					</c:forEach>
				</form:select>
			</li>
			<li><label>合同段编号：</label> 
				<form:select id="contractSectionLabelSelect" path="contractSectionLabel" class="input-medium">
					<form:option value="" label="---请选择---"/>
					<c:if test="${not empty contractlabels }">
						<c:forEach items="${contractlabels}" var="var">
							<form:option value="${var}" label="${var }"/>
						</c:forEach>
					</c:if>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></li>
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="submit" value="导出" /></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">项目名称</th>
				<th style="text-align: center">合同段编号</th>
				<th style="text-align: center">施工单位</th>
				<th style="text-align: center">姓名</th>
				<th style="text-align: center">性别</th>
				<th style="text-align: center">操作项目</th>
				<th style="text-align: center">执业证件编号</th>
				<th style="text-align: center">证书有效期</th>
				<th style="text-align: center">作业工点或位置</th>
				<th style="text-align: center">备注</th>
				<shiro:hasPermission name="safe:safeSpecOperPerson:edit">
					<th style="text-align: center">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="safeSpecOperPerson">
				<tr>
					<td><a
						href="${ctx}/safe/safeSpecOperPerson/form?id=${safeSpecOperPerson.id}">
							${safeSpecOperPerson.proSimpleInfo.projectSimpleName} </a></td>
					<td style="text-align: center">${safeSpecOperPerson.contractSectionLabel}</td>
					<td>${safeSpecOperPerson.constructionName}</td>
					<td>${safeSpecOperPerson.name}</td>
					<td style="text-align: center">${fns:getDictLabel(safeSpecOperPerson.gender, 'sex', '')}
					</td>
					<td>${safeSpecOperPerson.operateItems}</td>
					<td style="text-align: center">${safeSpecOperPerson.practiceCertnumber}</td>
					<td style="text-align: center"><fmt:formatDate
							value="${safeSpecOperPerson.certificateValid}"
							pattern="yyyy-MM-dd" /></td>
					<td>${safeSpecOperPerson.workSite}</td>
					<td>${safeSpecOperPerson.remarks}</td>
					<shiro:hasPermission name="safe:safeSpecOperPerson:edit">
						<td style="text-align: center"><a
							href="${ctx}/safe/safeSpecOperPerson/form?id=${safeSpecOperPerson.id}" title="编辑"><i class="icon-edit"></i></a>
							<a
							href="${ctx}/safe/safeSpecOperPerson/delete?id=${safeSpecOperPerson.id}"
							onclick="return confirmx('确认要删除该特种作业人员台帐吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>