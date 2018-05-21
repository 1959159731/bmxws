<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>安全生产管理人员管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	
	$(document).ready(function() {
		
		$("#projectNameSelect").on("change", function() {
			var _projectId = $(this).val();
			if ( _projectId ) {
				$.ajax({
					type: "POST",
					url: "${ctx}/safe/safeProManagerPerson/getContractLabel",
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
			$("#searchForm").attr("action", "${ctx}/safe/safeProManagerPerson/");
			$("#searchForm").submit();
		});
		
		
		$("#exportSubmit").on("click", function(){
			$("#searchForm").attr("action", "${ctx}/safe/safeProManagerPerson/export");
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
		<form id="importForm" action="${ctx}/safe/safeProManagerPerson/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/safe/safeProManagerPerson/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/safe/safeProManagerPerson/">安全生产管理人员列表</a></li>
		<shiro:hasPermission name="safe:safeProManagerPerson:edit">
			<li><a href="${ctx}/safe/safeProManagerPerson/form">安全生产管理人员添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="safeProManagerPerson" action="" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
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
				<th style="text-align: center">安全生产合格证书编号</th>
				<th style="text-align: center">证书有效期</th>
				<th style="text-align: center">专业及职称</th>
				<th style="text-align: center">职务</th>
				<th style="text-align: center">备注</th>
				<shiro:hasPermission name="safe:safeProManagerPerson:edit">
					<th style="text-align: center">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="safeProManagerPerson">
				<tr>
					<td><a
						href="${ctx}/safe/safeProManagerPerson/form?id=${safeProManagerPerson.id}">
							${safeProManagerPerson.proSimpleInfo.projectSimpleName} </a></td>
					<td style="text-align: center">${safeProManagerPerson.contractSectionLabel}</td>
					<td>${safeProManagerPerson.constructionName}</td>
					<td>${safeProManagerPerson.name}</td>
					<td style="text-align: center">${safeProManagerPerson.safetyProductionCertnumber}</td>
					<td style="text-align: center"><fmt:formatDate
							value="${safeProManagerPerson.certificateValid}"
							pattern="yyyy-MM-dd" /></td>
					<td>${safeProManagerPerson.jobTitle}</td>
					<td>${fns:getDictLabel(safeProManagerPerson.positon, 'safe_prodect_manger_position', '')}
					</td>
					<td>${safeProManagerPerson.remarks}</td>
					<shiro:hasPermission name="safe:safeProManagerPerson:edit">
						<td style="text-align: center"><a
							href="${ctx}/safe/safeProManagerPerson/form?id=${safeProManagerPerson.id}"
							title="编辑"><i class="icon-edit"></i></a> <a
							href="${ctx}/safe/safeProManagerPerson/delete?id=${safeProManagerPerson.id}"
							onclick="return confirmx('确认要删除该条吗？', this.href)" title="删除"><i
								class="icon-remove"></i></a></td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>