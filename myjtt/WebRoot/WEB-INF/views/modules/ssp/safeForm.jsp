<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>质量隐患随手拍</title>
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
		<li><a href="${ctx}/ssp/qualitySafe/">质量安全隐患随手拍</a></li>
		
		<li class="active">
			<a href="${ctx}/ssp/qualitySafe/form?id=${qualitySafe.id}">质量安全隐患
				<shiro:hasPermission name="ssp:qualitySafe:edit">${not empty qualitySafe.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="ssp:qualitySafe:edit">查看</shiro:lacksPermission>
			</a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="qualitySafe" action="${ctx}/ssp/qualitySafe/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">安全隐患简称：</label>
			<div class="controls">
				<form:input path="projectSimpleName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同段编号：</label>
			<div class="controls">
				<form:input path="contractSectionLabel" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患信息标题名称：</label>
			<div class="controls">
				<form:input path="dangerousTitle" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患信息描述：</label>
			<div class="controls">
				<form:input path="safeMessage" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
	
		<div class="control-group">
			<label class="control-label">照片：</label>
			<div class="controls">
				<form:textarea path="photo" htmlEscape="false" rows="4" maxlength="64" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ssp:qualitySafe:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
</body>
</html>