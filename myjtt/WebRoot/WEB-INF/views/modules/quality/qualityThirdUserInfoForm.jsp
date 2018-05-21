<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>检测单位工作内容汇总信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var username = "";
					var cid = "";
					var approachUsername = "";
					var approachCid = "";
					var approachUsers = "";
					
					$('input[name="username"]').each(function(){   
						username += $(this).val() + "-";   
					});
					
					$('input[name="cid"]').each(function(){   
						cid += $(this).val() + "-";   
					});
					
					$('input[name="approachUsername"]').each(function(){
						approachUsername += $(this).val() + "-";   
					});
					
					$('input[name="approachCid"]').each(function(){   
						approachCid += $(this).val() + "-";   
					});
					
					$("#approachUsers").val(username + "==" + cid + "==" + approachUsername + "==" + approachCid);
					
					if( approachUsername.split("-")[0].length > 0
							&& approachCid.split("-")[0].length > 0){
						loading('正在提交，请稍等...');
						form.submit();
					} else {
						alertx("请至少添加一名人员信息");
					}
					
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
			
			
			$("#addOneUser").on("click", function(){
				var str = '<div class="controls" style="margin-top: 5px;">'
						+ '<input type="text" name="username" placeholder="合同人员姓名" class="input-small" value="">'
						+ '&nbsp;<input type="text" name="cid" placeholder="身份证号码" class="input-large" value="">'
						+ '&nbsp;<input type="text" name="approachUsername" placeholder="实际进场人员姓名" class="input-small" value="">'
						+ '&nbsp;<input type="text" name="approachCid" placeholder="身份证号码" class="input-large" value="">'
						+ '</div>';
				$(this).parent().prev().after(str);
			});
			
			
		});
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/quality/qualityThirdUserInfo/">检测单位工作内容汇总信息列表</a></li>
		<li class="active"><a href="${ctx}/quality/qualityThirdUserInfo/form?id=${qualityThirdUserInfo.id}">检测单位工作内容汇总信息<shiro:hasPermission name="quality:qualityThirdUserInfo:edit">${not empty qualityThirdUserInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="quality:qualityThirdUserInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="qualityThirdUserInfo" action="${ctx}/quality/qualityThirdUserInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">合同段编号：</label>
			<div class="controls">
				<form:select path="qualityThirdCheckInfo.id" class="input-xlarge required">
					<form:option value="" label="---请选择---"/>
					<c:forEach items="${qualityThirdCheckInfoList}" var="var">
						<form:option value="${var.id}" label="${var.contractSectionLabel }"/>
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进场人员：</label>
			<form:hidden id="approachUsers" path="approachUser" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			
			<c:if test="${empty qualityThirdUserInfo.userList }">
				<div class="controls">
					<input type="text" name="username" placeholder="合同人员姓名" class="input-small" value="">
					<input type="text" name="cid" placeholder="身份证号码" class="input-large" value="">
					<input type="text" name="approachUsername" placeholder="实际进场人员姓名" class="input-small" value="">
					<input type="text" name="approachCid" placeholder="身份证号码" class="input-large" value="">
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</c:if>
			<c:if test="${not empty qualityThirdUserInfo.userList }">
				<c:forEach items="${qualityThirdUserInfo.userList }" var="item" varStatus="index">
					<div class="controls" style="margin-top: 5px;">
						<input type="text" name="username" placeholder="合同人员姓名" class="input-small" value="${item[0] }">
						<input type="text" name="cid" placeholder="身份证号码" class="input-large" value="${item[1] }">
						<input type="text" name="approachUsername" placeholder="实际进场人员姓名" class="input-small" value="${item[2] }">
						<input type="text" name="approachCid" placeholder="身份证号码" class="input-large" value="${item[3] }">
						<c:if test="${index.index == 0 }">
							<span class="help-inline"><font color="red">*</font> </span>
						</c:if>
					</div>
				</c:forEach>
			</c:if>
			
			<div class="controls" style="margin-top: 5px;">
				<input id="addOneUser" class="btn btn-success" type="button" value="继续添加人员"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检测内容：</label>
			<div class="controls">
				<form:textarea path="contractContext" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">办公场所、仪器设备到位情况：</label>
			<div class="controls">
				<form:textarea path="officeEqui" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="quality:qualityThirdUserInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>