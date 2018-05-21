<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>质量隐患管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/ssp/qualitySafe/");
				$("#searchForm").submit();
			});
		});
		
		$("#btnUpload").click(function() {
			$.jBox($("#btnUpload").html(), {
				title : "导入数据",
				buttons : {
					"关闭" : true
				},
				bottomText : "导入文件不能超过5M，仅允许导入“.png”或“.jpg”格式文件！"
			});
		});
		
		$("#btnDowload").click(function(){
			alert("Hello");
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
		<li class="active"><a href="${ctx}/ssp/qualitySafe/">质量隐患列表</a></li>
		<shiro:hasPermission name="ssp:qualitySafe:edit">
			<li><a href="${ctx}/ssp/qualitySafe/form">质量隐患添加</a></li>
		</shiro:hasPermission>		
	</ul>
	<form:form id="searchForm" modelAttribute="qualitySafe" action="${ctx}/ssp/qualitySafe/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">隐患简称</th>
				<th style="text-align: center">合同段编号</th>
				<th style="text-align: center">隐患信息标题名称</th>
				<th style="text-align: center">隐患信息描述</th>
				
				<th style="text-align: center">照片</th>
				
				<shiro:hasPermission name="ssp:qualitySafe:edit">
					<th style="text-align: center">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${requestScope.list}" var="qualitySafe">
			<tr>
				<td>
					<a href="${ctx}/ssp/qualitySafe/form?id=${qualitySafe.id}"></a>
					${qualitySafe.projectSimpleName}
					
				</td>
				<td>
					${qualitySafe.contractSectionLabel}
				</td>
				<td>
					${qualitySafe.dangerousTitle}
				</td>
				<td>
					
					${qualitySafe.safeMessage}
				</td>
				<td style="width:145px">
					
					&nbsp;&nbsp;&nbsp;<a id="btnSee" href="${ctx}/ssp/qualitySafe/see?id=${qualitySafe.projectSimpleName}">查看</a>&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;<a id="btnDowload"    href="${ctx}/ssp/qualitySafe/dowload?id=${qualitySafe.projectSimpleName}">下载</a>&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;<a id="btnUpload"  href="${ctx}/ssp/qualitySafe/upload?id=${qualitySafe.projectSimpleName}">上传</a>
				</td>
				
			
				<shiro:hasPermission name="ssp:qualitySafe:edit">
				<td style="text-align: center">
    				<a href="${ctx}/ssp/qualitySafe/form?id=${qualitySafe.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/ssp/qualitySafe/delete?id=${qualitySafe.id}" onclick="return confirmx('确认要删除该质量隐患吗？', this.href)" title="删除""><i class="icon-remove"></i></a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>