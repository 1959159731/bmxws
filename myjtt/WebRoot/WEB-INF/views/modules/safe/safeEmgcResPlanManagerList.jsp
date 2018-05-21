<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>应急救援预案管理管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#projectNameSelect").on("change", function() {
			var _projectId = $(this).val();
			if ( _projectId ) {
				$.ajax({
					type: "POST",
					url: "${ctx}/safe/safeEmgcResPlanManager/getContractLabel",
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
			$("#searchForm").attr("action", "${ctx}/safe/safeEmgcResPlanManager/");
			$("#searchForm").submit();
		});
		
		
		$("#exportSubmit").on("click", function(){
			$("#searchForm").attr("action", "${ctx}/safe/safeEmgcResPlanManager/export");
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
		<form id="importForm" action="${ctx}/safe/safeEmgcResPlanManager/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/safe/safeEmgcResPlanManager/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/safe/safeEmgcResPlanManager/">应急救援预案管理列表</a></li>
		<shiro:hasPermission name="safe:safeEmgcResPlanManager:edit">
			<li><a href="${ctx}/safe/safeEmgcResPlanManager/form">应急救援预案管理添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="safeEmgcResPlanManager"
		action="${ctx}/safe/safeEmgcResPlanManager/" method="post"
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
				<th style="text-align: center">预案类型</th>
				<th style="text-align: center">应急救援预案名称</th>
				<th style="text-align: center">单位负责人签批情况</th>
				<th style="text-align: center">实施日期</th>
				<th style="text-align: center">是否上报相关部门备案</th>
				<th style="text-align: center">应急演练时间</th>
				<th style="text-align: center">备注</th>
				<th style="text-align: center">附件下载</th>
				<shiro:hasPermission name="safe:safeEmgcResPlanManager:edit">
					<th style="text-align: center">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="safeEmgcResPlanManager">
				<tr>
					<td><a
						href="${ctx}/safe/safeEmgcResPlanManager/form?id=${safeEmgcResPlanManager.id}">
							${safeEmgcResPlanManager.proSimpleInfo.projectSimpleName} </a></td>
					<td style="text-align: center">${safeEmgcResPlanManager.contractSectionLabel}</td>
					<td>${safeEmgcResPlanManager.constructionName}</td>
					<td style="text-align: center">${fns:getDictLabel(safeEmgcResPlanManager.planType, 'safe_emgc_res_plan', '')}
					</td>
					<td>${safeEmgcResPlanManager.emergencyRescueName}</td>
					<td>${safeEmgcResPlanManager.signSituation}</td>
					<td style="text-align: center"><fmt:formatDate
							value="${safeEmgcResPlanManager.implementationDate}"
							pattern="yyyy-MM-dd" /></td>
					<td style="text-align: center">
						${fns:getDictLabel(safeEmgcResPlanManager.reportDepartmentRecord, 'yes_no', '')}
					</td>
					<td style="text-align: center">
						<c:if test="${empty safeEmgcResPlanManager.emergencyDrillTime }">无</c:if>
						<c:if test="${not empty safeEmgcResPlanManager.emergencyDrillTime }">
							<fmt:formatDate
								value="${safeEmgcResPlanManager.emergencyDrillTime }"
								pattern="yyyy-MM-dd" />
						</c:if>
					</td>
					<td>${safeEmgcResPlanManager.remarks}</td>
					<td style="text-align: center">
						<a href="${ctx}/safe/safeEmgcResPlanManager/download?dangeProId=${safeEmgcResPlanManager.id}" title="导出"><i class="icon-download-alt"></i></a> 
					</td>
					<shiro:hasPermission name="safe:safeEmgcResPlanManager:edit">
						<td style="text-align: center"><a
							href="${ctx}/safe/safeEmgcResPlanManager/form?id=${safeEmgcResPlanManager.id}" title="编辑"><i class="icon-edit"></i></a>
							<a
							href="${ctx}/safe/safeEmgcResPlanManager/delete?id=${safeEmgcResPlanManager.id}"
							onclick="return confirmx('确认要删除该应急救援预案管理吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
						</td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>