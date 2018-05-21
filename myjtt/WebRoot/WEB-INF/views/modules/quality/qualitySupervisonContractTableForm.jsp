<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监理单位履约对照管理</title>
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
			
			
			
			// 加载合同编号Select
			$("#projectIds").on("change", function(){
				var _projectId = $(this).val();
				if( _projectId ){
					$.ajax({
				        type: "POST",
				        url : "${ctx}/quality/qualitySupervisonInfo/getSupervisonCompanyName",
				        data: {
				        	projectId: _projectId
				        },
		        		success : function(data) {
		        			$("#supervisionNameSelect").empty();
		        	    	$("<option value=''>---请选择---</option>").appendTo($("#supervisionNameSelect"));
		        	    	$.each(data, function(index, item){
		        	            $("<option value='" + item + "'>" + item + "</option>").appendTo($("#supervisionNameSelect"));
		        			});
		        	    	$("#supervisionNameSelect").change();
				        }
				    });
				}
			});
			
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/quality/qualitySupervisonContractTable/">监理单位履约对照列表</a></li>
		<li class="active"><a href="${ctx}/quality/qualitySupervisonContractTable/form?id=${qualitySupervisonContractTable.id}">监理单位履约对照<shiro:hasPermission name="quality:qualitySupervisonContractTable:edit">${not empty qualitySupervisonContractTable.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="quality:qualitySupervisonContractTable:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="qualitySupervisonContractTable" action="${ctx}/quality/qualitySupervisonContractTable/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:select id="projectIds" path="proSimpleInfo.id" class="input-xlarge required" style="width: 284px;">
					<form:option value="" label=""/>
					<c:forEach items="${projectList}" var="var">
						<form:option value="${var.id }" label="${var.projectSimpleName }"/>
					</c:forEach>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监理单位名称：</label>
			<div class="controls">
				<form:select id="supervisionNameSelect" path="supervisionName" class="input-xlarge required" style="width: 284px;">
					<form:option value="" label=""/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同总人数：</label>
			<div class="controls">
				<form:input path="contractTotalNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同人员持部证人数：</label>
			<div class="controls">
				<form:input path="contractDepartNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同人员持培训证人数：</label>
			<div class="controls">
				<form:input path="contractTrainingNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际进场人员总人数：</label>
			<div class="controls">
				<form:input path="incomingTotalNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际进场持部证人数：</label>
			<div class="controls">
				<form:input path="incomingDepartNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际进场人员持培训证人数：</label>
			<div class="controls">
				<form:input path="incomingTrainingNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际进场人员持证率（%）：</label>
			<div class="controls">
				<form:input path="incomingCertificateRate" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部证更换人数：</label>
			<div class="controls">
				<form:input path="departReplaceNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">培训证更换人数：</label>
			<div class="controls">
				<form:input path="trainingReplaceNum" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">高驻是否更换：</label>
			<div class="controls">
				<form:select path="inhighIsReplaced" class="input-xlarge required" style="width: 284px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更换率（%）：</label>
			<div class="controls">
				<form:input path="replcaedRate" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="quality:qualitySupervisonContractTable:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>