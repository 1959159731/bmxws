<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>第三方检测单位信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					
					if(!/^1[0-9]{10}$/.test($("#onsiteLeaderTelphone").val())){		// 现场检查负责人联系电话
						alertx("请输入正确的电话号码");
						return false;
					}
					
					if(!/^1[0-9]{10}$/.test($("#inspectionLeaderTelphone").val())){	// 检测单位负责人联系电话
						alertx("请输入正确的电话号码");
						return false;
					}
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
			
			$("#onsiteLeaderTelphone").on("blur", function(){
				var telPhone = $(this).val();
				var reg = /^1[0-9]{10}$/;
				if(!reg.test(telPhone)){
					alertx("请输入正确的电话号码");
					return;
				}
			});
			
			$("#inspectionLeaderTelphone").on("blur", function(){
				var telPhone = $(this).val();
				var reg = /^1[0-9]{10}$/;
				if(!reg.test(telPhone)){
					alertx("请输入正确的电话号码");
					return;
				}
			});
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/quality/qualityThirdCheckInfo/">第三方检测单位信息列表</a></li>
		<li class="active">
			<a href="${ctx}/quality/qualityThirdCheckInfo/form?id=${qualityThirdCheckInfo.id}">第三方检测单位信息
				<shiro:hasPermission name="quality:qualityThirdCheckInfo:edit">${not empty qualityThirdCheckInfo.id?'修改':'添加'}
				</shiro:hasPermission><shiro:lacksPermission name="quality:qualityThirdCheckInfo:edit">查看</shiro:lacksPermission>
			</a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="qualityThirdCheckInfo" action="${ctx}/quality/qualityThirdCheckInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:select path="proSimpleInfo.id" class="input-xlarge required" style="width: 284px;">
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
				<form:input path="contractSectionLabel" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检测单位：</label>
			<div class="controls">
				<form:input path="detectionUnit" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所检测合同段：</label>
			<div class="controls">
				<form:input path="indetectionContractSectionLabel" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检测单位负责人：</label>
			<div class="controls">
				<form:input path="inspectionLeader" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检测单位负责人联系电话：</label>
			<div class="controls">
				<form:input id="inspectionLeaderTelphone" path="inspectionLeaderPhonenum" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现场检查负责人：</label>
			<div class="controls">
				<form:input path="onsiteInspectionLeader" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现场检查负责人联系电话：</label>
			<div class="controls">
				<form:input id="onsiteLeaderTelphone" path="onsiteInspectionLeaderPhonenum" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="quality:qualityThirdCheckInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>