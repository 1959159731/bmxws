<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监理单位信息管理</title>
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
					$("#contractSectionLabel").empty();
					$("#contractSectionLabel").attr("onkeyup", "value=value.replace(/[^\\d]/g,'')");
					$("#contractSectionLabel").attr("placeholder", "只能输入数字");
				} else {
					$("#contractSectionLabel").attr("onkeyup", "");
					$("#contractSectionLabel").attr("placeholder", "");
				}
			});
			
			
		});
		
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/quality/qualitySupervisonInfo/">监理单位信息列表</a></li>
		<li class="active"><a href="${ctx}/quality/qualitySupervisonInfo/form?id=${qualitySupervisonInfo.id}">监理单位信息<shiro:hasPermission name="quality:qualitySupervisonInfo:edit">${not empty qualitySupervisonInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="quality:qualitySupervisonInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="qualitySupervisonInfo" action="${ctx}/quality/qualitySupervisonInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目：</label>
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
			<label class="control-label">监理合同段编号：</label>
			<div class="controls">
				<form:select path="contractSectionType" class="input-small required disabled" style="width: 65px;">
					<form:option value="1" label="JL-"/>
					<form:option value="6" label="其它"/>
				</form:select>
				<form:input path="contractSectionLabel" htmlEscape="false" maxlength="64" class="input-large required" 
						style="margin-left: -5px; height: 18px; width: 206px;" 
						placeholder="只能输入数字"
						onkeyup="value=value.replace(/[^\\d.]/g,'')" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理单位名称：</label>
			<div class="controls">
				<form:input path="supervisionName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所管辖合施工同段编号：</label>
			<div class="controls">
				<form:input path="haveContractSectionLabel" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起讫桩号：</label>
			<div class="controls">
				<form:input path="startingStation" htmlEscape="false" maxlength="64" class="input-small required"/> - 
				<form:input path="endingStation" htmlEscape="false" maxlength="64" class="input-small required" style="width: 124px;"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理服务费(万元)：</label>
			<div class="controls">
				<form:input path="supervisionServiceFee" htmlEscape="false" class="input-xlarge required" 
						placeholder="只能输入数字"
						onkeyup="value=value.replace(/[^\\d.]/g,'')" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理工程造价（万元）：</label>
			<div class="controls">
				<form:input path="supervisionProjectCosts" htmlEscape="false" class="input-xlarge required" 
						placeholder="只能输入数字"
						onkeyup="value=value.replace(/[^\\d.]/g,'')" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理合同开始时间：</label>
			<div class="controls">
				<input name="supervisionContractStart" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${qualitySupervisonInfo.supervisionContractStart}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理合同结束时间：</label>
			<div class="controls">
				<input name="supervisionContractEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${qualitySupervisonInfo.supervisionContractEnd}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理项目负责人：</label>
			<div class="controls">
				<form:input path="principal" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
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
			<shiro:hasPermission name="quality:qualitySupervisonInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>