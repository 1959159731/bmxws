<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>主要结构物隧道管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/project/proSimpleStructureTunnel/");
				$("#searchForm").submit();
			});
			
			
			$("#exportSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/project/proSimpleStructureTunnel/export");
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
		<li class="active"><a href="${ctx}/project/proSimpleStructureTunnel/">主要结构物隧道列表</a></li>
		<shiro:hasPermission name="project:proSimpleStructureTunnel:edit"><li><a href="${ctx}/project/proSimpleStructureTunnel/form">主要结构物隧道添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="proSimpleStructureTunnel" action="" method="post" class="breadcrumb form-search">
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
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">项目名称</th>
				<th style="text-align: center">合同段编号</th>
				<th style="text-align: center">结构物名称</th>
				<th style="text-align: center">桩号</th>
				<th style="text-align: center">长度（m）</th>
				<th style="text-align: center">结构物形式</th>
				<th style="text-align: center">工程主要技术特点、难点</th>
				<th style="text-align: center">备注</th>
				<shiro:hasPermission name="project:proSimpleStructureTunnel:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="proSimpleStructureTunnel">
			<tr>
				<td><a href="${ctx}/project/proSimpleStructureTunnel/form?id=${proSimpleStructureTunnel.id}">
					${proSimpleStructureTunnel.proSimpleInfo.projectSimpleName}
				</a></td>
				<td>
					${proSimpleStructureTunnel.contractSectionLabel}
				</td>
				<td>
					${proSimpleStructureTunnel.structureName}
				</td>
				<td>
					${proSimpleStructureTunnel.stake}
				</td>
				<td style="text-align: right">
					${proSimpleStructureTunnel.length}
				</td>
				<td>
					${proSimpleStructureTunnel.structureType}
				</td>
				<td>
					${proSimpleStructureTunnel.technicalCharacter}
				</td>
				<td>
					${proSimpleStructureTunnel.remarks}
				</td>
				<shiro:hasPermission name="project:proSimpleStructureTunnel:edit"><td style="text-align: center">
    				<a href="${ctx}/project/proSimpleStructureTunnel/form?id=${proSimpleStructureTunnel.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/project/proSimpleStructureTunnel/delete?id=${proSimpleStructureTunnel.id}" onclick="return confirmx('确认要删除该主要结构物隧道吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>