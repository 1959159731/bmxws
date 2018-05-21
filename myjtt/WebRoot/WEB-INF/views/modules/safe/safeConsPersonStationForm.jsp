<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>施工人员驻地管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			
			// 加载合同编号类型
			$("#contractSectionType").on("change", function(){
				var _contractSectionType = $(this).val();
				console.log(_contractSectionType);
				if( _contractSectionType != 6 ){
					$("#contractLabelId").empty();
					$("#contractLabelId").attr("onkeyup", "value=value.replace(/[^\\d]/g,'')");
					$("#contractLabelId").attr("placeholder", "只能输入数字");
				} else {
					$("#contractLabelId").attr("onkeyup", "");
					$("#contractLabelId").attr("placeholder", "");
				}
			});
			
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/safe/safeConsPersonStation/">施工人员驻地管理列表</a></li>
		<li class="active"><a href="${ctx}/safe/safeConsPersonStation/form?id=${safeConsPersonStation.id}">施工人员驻地管理<shiro:hasPermission name="safe:safeConsPersonStation:edit">${not empty safeConsPersonStation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="safe:safeConsPersonStation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="safeConsPersonStation" action="${ctx}/safe/safeConsPersonStation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:select path="proSimpleInfo.id" class="input-xlarge required" style="width: 285px;">
					<form:option value="" label=""/>
					<c:forEach items="${projectList}" var="var">
						<form:option value="${var.id }" label="${var.projectSimpleName }"/>
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同段编号：</label>
			<div class="controls">
				<form:select path="contractSectionType" class="input-small required" style="width: 100px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('safe_project_contract_notype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:input id="contractLabelId" path="contractSectionLabel" htmlEscape="false" maxlength="64" class="input-large required" style="margin-left: -4px; height: 18px; width: 172px;"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">施工单位：</label>
			<div class="controls">
				<form:input path="constructionName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">驻地来源：</label>
			<div class="controls">
				<form:select path="residentSources" class="input-xlarge required" style="width: 284px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('safe_resident_sources')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">驻地类型：</label>
			<div class="controls">
				<form:select path="residentType" class="input-xlarge required" style="width: 284px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('safe_resident_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">驻地位置：</label>
			<div class="controls">
				<form:input path="residentSite" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">居住的班组和工队名称：</label>
			<div class="controls">
				<form:input path="teamName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">居住人数：</label>
			<div class="controls">
				<form:input path="liveNum" placeholder="请输入正整数" htmlEscape="false" maxlength="12" class="input-xlarge required" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> 请填写消防器材配备情况</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="safe:safeConsPersonStation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>