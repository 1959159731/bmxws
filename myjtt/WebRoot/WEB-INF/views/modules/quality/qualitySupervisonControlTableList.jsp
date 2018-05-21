<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监理单位进场人员信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualitySupervisonControlTable/");
				$("#searchForm").submit();
			});
			
			
			$("#exportSubmit").on("click", function(){
				$("#searchForm").attr("action", "${ctx}/quality/qualitySupervisonControlTable/export");
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
		<form id="importForm" action="${ctx}/quality/qualitySupervisonControlTable/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width: 330px" /><br/>
			<br/>
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " /> 
			<a href="${ctx}/quality/qualitySupervisonControlTable/import/template" class="btn btn-success">下载模板</a>
		</form>
	</div>
	
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/quality/qualitySupervisonControlTable/">监理单位进场人员信息列表</a></li>
		<shiro:hasPermission name="quality:qualitySupervisonControlTable:edit"><li><a href="${ctx}/quality/qualitySupervisonControlTable/form">监理单位进场人员信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="qualitySupervisonControlTable" action="" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: 120px;">监理单位：</label>
				<form:select path="supervisonName" class="input-medium"  style="width: 177px;">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${supList}" var="item">
						<form:option value="${item}" label="${item }"/>
					</c:forEach>
				</form:select>
			</li>
			<li class="btns"><input id="exportSubmit" class="btn btn-primary" type="submit" value="导出"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			<li class="clearfix"></li>
		</ul>
		<ul class="ul-form">
			<li><label style="width: 120px;">进场人员姓名：</label>
				<form:input path="incomingUsername" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>注册情况：</label>
				<form:select path="isRegister" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2" style="text-align: center">监理单位</th>
				<th colspan="3" style="text-align: center">合同人员</th>
				<th colspan="7" style="text-align: center">实际进场人员</th>
				<th rowspan="2" style="text-align: center">备注</th>
				<shiro:hasPermission name="quality:qualitySupervisonControlTable:edit"><th rowspan="2" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
			<tr>
				<th style="text-align: center">合同人员姓名</th>
				<th style="text-align: center">合同人员岗位</th>
				<th style="text-align: center">合同人员职称</th>
				<th style="text-align: center">进场人员姓名</th>
				<th style="text-align: center">进场人员岗位</th>
				<th style="text-align: center">进场人员职称</th>
				<th style="text-align: center">进场人员身份证号码</th>
				<th style="text-align: center">监理资格证书编号</th>
				<th style="text-align: center">注册情况</th>
				<th style="text-align: center">进场人员进场时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualitySupervisonControlTable">
			<tr>
				<td><a href="${ctx}/quality/qualitySupervisonControlTable/form?id=${qualitySupervisonControlTable.id}">
					${qualitySupervisonControlTable.supervisonName}
				</a></td>
				<td>
					${qualitySupervisonControlTable.contractUsername}
				</td>
				<td>
					${qualitySupervisonControlTable.contractPost}
				</td>
				<td>
					${qualitySupervisonControlTable.contractJobTitle}
				</td>
				<td>
					${qualitySupervisonControlTable.incomingUsername}
				</td>
				<td>
					${qualitySupervisonControlTable.incomingPost}
				</td>
				<td>
					${qualitySupervisonControlTable.incomingJobTitle}
				</td>
				<td style="text-align: center">
					${qualitySupervisonControlTable.incomingCid}
				</td>
				<td style="text-align: center">
					${qualitySupervisonControlTable.incomingCertificateNo}
				</td>
				<td style="text-align: center">
					${fns:getDictLabel(qualitySupervisonControlTable.isRegister, 'yes_no', '')}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${qualitySupervisonControlTable.incomingApproachTime}" pattern="yyyy-MM"/>
				</td>
				<td>
					${qualitySupervisonControlTable.remarks}
				</td>
				<shiro:hasPermission name="quality:qualitySupervisonControlTable:edit"><td style="text-align: center">
    				<a href="${ctx}/quality/qualitySupervisonControlTable/form?id=${qualitySupervisonControlTable.id}" title="编辑"><i class="icon-edit"></i></a>
					<a href="${ctx}/quality/qualitySupervisonControlTable/delete?id=${qualitySupervisonControlTable.id}" onclick="return confirmx('确认要删除该监理单位进场人员信息吗？', this.href)" title="删除"><i class="icon-remove"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>