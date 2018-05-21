<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监理单位进场人员信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					if(!getIdCardInfo($("#cidid").val()).isTrue){
						alertx("请输入正确的身份证号码");
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
			
			$("#cidid").on("blur", function(){
				var cid = $(this).val();
				if(!getIdCardInfo(cid).isTrue){
					alertx("请输入正确的身份证号码");
				}
 			});
			
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/quality/qualitySupervisonControlTable/">监理单位进场人员信息列表</a></li>
		<li class="active"><a href="${ctx}/quality/qualitySupervisonControlTable/form?id=${qualitySupervisonControlTable.id}">监理单位进场人员信息<shiro:hasPermission name="quality:qualitySupervisonControlTable:edit">${not empty qualitySupervisonControlTable.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="quality:qualitySupervisonControlTable:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="qualitySupervisonControlTable" action="${ctx}/quality/qualitySupervisonControlTable/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">监理单位：</label>
			<div class="controls">
				<form:select path="supervisonName" class="input-xlarge required" style="width: 284px;">
					<form:option value="" label=""/>
					<c:forEach items="${supList}" var="var">
						<form:option value="${var }" label="${var }"/>
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同人员姓名：</label>
			<div class="controls">
				<form:input path="contractUsername" htmlEscape="false" maxlength="64" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同人员岗位：</label>
			<div class="controls">
				<form:input path="contractPost" htmlEscape="false" maxlength="64" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同人员职称：</label>
			<div class="controls">
				<form:input path="contractJobTitle" htmlEscape="false" maxlength="64" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进场人员姓名：</label>
			<div class="controls">
				<form:input path="incomingUsername" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进场人员岗位：</label>
			<div class="controls">
				<form:input path="incomingPost" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进场人员职称：</label>
			<div class="controls">
				<form:input path="incomingJobTitle" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进场人员身份证号码：</label>
			<div class="controls">
				<form:input id="cidid" path="incomingCid" htmlEscape="false" maxlength="18" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理资格证书编号：</label>
			<div class="controls">
				<form:input path="incomingCertificateNo" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册情况：</label>
			<div class="controls">
				<form:select path="isRegister" class="input-xlarge required" style="width: 284px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进场人员进场时间：</label>
			<div class="controls">
				<input name="incomingApproachTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${qualitySupervisonControlTable.incomingApproachTime}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
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
			<shiro:hasPermission name="quality:qualitySupervisonControlTable:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>