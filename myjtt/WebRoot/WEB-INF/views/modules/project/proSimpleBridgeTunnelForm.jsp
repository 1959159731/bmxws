<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目桥梁、隧道信息管理</title>
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/project/proSimpleBridgeTunnel/">项目桥梁、隧道信息列表</a></li>
		<li class="active"><a href="${ctx}/project/proSimpleBridgeTunnel/form?id=${proSimpleBridgeTunnel.id}">项目桥梁、隧道信息<shiro:hasPermission name="project:proSimpleBridgeTunnel:edit">${not empty proSimpleBridgeTunnel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="project:proSimpleBridgeTunnel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="proSimpleBridgeTunnel" action="${ctx}/project/proSimpleBridgeTunnel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">project_id：</label>
			<div class="controls">
				<form:input path="projectId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特大桥数量（座）：</label>
			<div class="controls">
				<form:input path="extraLargeBridgeNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计长度（m）：</label>
			<div class="controls">
				<form:input path="extraLargeBridgeLength" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">大桥（座）：</label>
			<div class="controls">
				<form:input path="largeBridgeNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计长度（m）：</label>
			<div class="controls">
				<form:input path="largeBridgeLength" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">中桥（座）：</label>
			<div class="controls">
				<form:input path="mediumBridgeNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计长度（m）：</label>
			<div class="controls">
				<form:input path="mediumBridgeLength" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特长隧道（座）：</label>
			<div class="controls">
				<form:input path="extraLongTunnelNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计长度（m）：</label>
			<div class="controls">
				<form:input path="extraLongTunnelLength" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">长隧道（座）：</label>
			<div class="controls">
				<form:input path="longTunnelNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计长度（m）：</label>
			<div class="controls">
				<form:input path="longTunnelLength" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">中隧道（座）：</label>
			<div class="controls">
				<form:input path="mediumTunnelNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计长度（m）：</label>
			<div class="controls">
				<form:input path="mediumTunnelLength" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">短隧道（座）：</label>
			<div class="controls">
				<form:input path="shortTunnelNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">累计长度（m）：</label>
			<div class="controls">
				<form:input path="shortTunnelLength" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="project:proSimpleBridgeTunnel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>