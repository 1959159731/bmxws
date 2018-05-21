<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监理单位履约对照管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualitySupervisonContractTable/");
				$("#searchForm").submit();
			});
			
			
			$("#exportSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualitySupervisonContractTable/export");
				$("#searchForm").submit();
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/quality/qualitySupervisonContractTable/">监理单位履约对照列表</a></li>
		<shiro:hasPermission name="quality:qualitySupervisonContractTable:edit"><li><a href="${ctx}/quality/qualitySupervisonContractTable/form">监理单位履约对照添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualitySupervisonContractTable" action="" method="post" class="breadcrumb form-search">
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="submit" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2" style="text-align: center">项目</th>
				<th rowspan="2" style="text-align: center">监理单位</th>
				<th colspan="3" style="text-align: center">合同人员</th>
				<th colspan="4" style="text-align: center">实际进场人员</th>
				<th rowspan="2" style="text-align: center">部证更换人数</th>
				<th rowspan="2" style="text-align: center">培训证更换人数</th>
				<th rowspan="2" style="text-align: center">高驻是否更换</th>
				<th rowspan="2" style="text-align: center">更换率（%）</th>
				<th rowspan="2" style="text-align: center">备注</th>
				<shiro:hasPermission name="quality:qualitySupervisonContractTable:edit"><th rowspan="2" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
			<tr>
				<th style="text-align: center">总人数</th>
				<th style="text-align: center">持部证人数</th>
				<th style="text-align: center">持培训证人数</th>
				<th style="text-align: center">实际总人数</th>
				<th style="text-align: center">持部证人数</th>
				<th style="text-align: center">持培训证人数</th>
				<th style="text-align: center">持证率（%）</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualitySupervisonContractTable">
			<tr>
				<td><a href="${ctx}/quality/qualitySupervisonContractTable/form?id=${qualitySupervisonContractTable.id}">
					${qualitySupervisonContractTable.proSimpleInfo.projectSimpleName}
				</a></td>
				<td>
					${qualitySupervisonContractTable.supervisionName}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.contractTotalNum}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.contractDepartNum}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.contractTrainingNum}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.incomingTotalNum}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.incomingDepartNum}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.incomingTrainingNum}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.incomingCertificateRate}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.departReplaceNum}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.trainingReplaceNum}
				</td>
				<td style="text-align: center">
					${fns:getDictLabel(qualitySupervisonContractTable.inhighIsReplaced, 'yes_no', '')}
				</td>
				<td style="text-align: right">
					${qualitySupervisonContractTable.replcaedRate}
				</td>
				<td>
					${qualitySupervisonContractTable.remarks}
				</td>
				<shiro:hasPermission name="quality:qualitySupervisonContractTable:edit"><td style="text-align: center">
    				<a href="${ctx}/quality/qualitySupervisonContractTable/form?id=${qualitySupervisonContractTable.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualitySupervisonContractTable/delete?id=${qualitySupervisonContractTable.id}" onclick="return confirmx('确认要删除该监理单位履约对照吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>