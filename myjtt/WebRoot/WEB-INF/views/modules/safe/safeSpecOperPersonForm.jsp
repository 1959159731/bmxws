<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>特种作业人员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var _imgPath = $("#nameImage").val();
					if(!_imgPath){
						alertx("请上传证书扫描件");
						return;
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
		<li><a href="${ctx}/safe/safeSpecOperPerson/">特种作业人员列表</a></li>
		<li class="active"><a href="${ctx}/safe/safeSpecOperPerson/form?id=${safeSpecOperPerson.id}">特种作业人员<shiro:hasPermission name="safe:safeSpecOperPerson:edit">${not empty safeSpecOperPerson.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="safe:safeSpecOperPerson:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="safeSpecOperPerson" action="${ctx}/safe/safeSpecOperPerson/save" method="post" class="form-horizontal">
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
				<form:input id="contractLabelId" path="contractSectionLabel" htmlEscape="false" maxlength="64" class="input-large required" style="margin-left: -5px; height: 18px; width: 172px;"/>
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
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="12" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="gender" class="input-xlarge required" style="width: 284px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作项目：</label>
			<div class="controls">
				<form:input path="operateItems" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作业工点或位置：</label>
			<div class="controls">
				<form:input path="workSite" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执业证件编号：</label>
			<div class="controls">
				<form:input path="practiceCertnumber" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证书有效期：</label>
			<div class="controls">
				<input name="certificateValid" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${safeSpecOperPerson.certificateValid}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证书：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="certificatePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
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
			<shiro:hasPermission name="safe:safeSpecOperPerson:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>