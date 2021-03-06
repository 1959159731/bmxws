<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>检测单位工作内容汇总信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityThirdUserInfo/");
				$("#searchForm").submit();
			});
			
			
			$("#exportSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualityThirdUserInfo/export");
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
		<form id="importForm" action="${ctx}/quality/qualityThirdUserInfo/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/quality/qualityThirdUserInfo/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/quality/qualityThirdUserInfo/">检测单位工作内容汇总信息列表</a></li>
		<shiro:hasPermission name="quality:qualityThirdUserInfo:edit"><li><a href="${ctx}/quality/qualityThirdUserInfo/form">检测单位工作内容汇总信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualityThirdUserInfo" action="" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>合同段编号：</label>
				<form:select path="qualityThirdCheckInfo.id" class="input-medium">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${qualityThirdCheckInfoList}" var="var">
						<form:option value="${var.id}" label="${var.contractSectionLabel }"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="submit" value="导出"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align: center">合同段编号</th>
				<th style="text-align: center">检测内容</th>
				<th style="text-align: center">办公场所、仪器设备到位情况</th>
				<th style="text-align: center">备注</th>
				<shiro:hasPermission name="quality:qualityThirdUserInfo:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualityThirdUserInfo">
			<tr>
				<td><a href="${ctx}/quality/qualityThirdUserInfo/form?id=${qualityThirdUserInfo.id}">
					${qualityThirdUserInfo.qualityThirdCheckInfo.contractSectionLabel}
				</a></td>
				<td>
					${qualityThirdUserInfo.contractContext}
				</td>
				<td>
					${qualityThirdUserInfo.officeEqui}
				</td>
				<td>
					${qualityThirdUserInfo.remarks}
				</td>
				<shiro:hasPermission name="quality:qualityThirdUserInfo:edit"><td style="text-align: center">
    				<a href="${ctx}/quality/qualityThirdUserInfo/form?id=${qualityThirdUserInfo.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualityThirdUserInfo/delete?id=${qualityThirdUserInfo.id}" onclick="return confirmx('确认要删除该检测单位工作内容汇总信息吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>