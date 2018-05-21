<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中心试验室备案信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					
					var username = "";
					var qualification = "";	// 从业资格
					var qCertNum = "";		// 资格证书编号
					var remark = "";		// 备注
					
					var approachUsers = "";
					
					$('input[name="username"]').each(function(){   
						username += $(this).val() + "--";   
					});
					
					$('input[name="qualification"]').each(function(){   
						qualification += $(this).val() + "--";   
					});
					
					$('input[name="qCertNum"]').each(function(){
						qCertNum += $(this).val() + "--";   
					});
					
					$('input[name="remark"]').each(function(){   
						remark += $(this).val() + "--";   
					});
					
					$("#remarkUser").val(username + "==" + qualification + "==" + qCertNum + "==" + remark);
					
					if( username.split("--")[0].length > 0 
							&& qualification.split("--")[0].length > 0 
							&& qCertNum.split("--")[0].length > 0){
						loading('正在提交，请稍等...');
						form.submit();
					} else {
						alertx("请至少添加一名备案人员信息");
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
						+ '<input type="text" name="username" placeholder="姓名" class="input-small" value="">'
						+ '&nbsp;<input type="text" name="qualification" placeholder="从业资格" class="input-large" value="">'
						+ '&nbsp;<input type="text" name="qCertNum" placeholder="资格证书编号" class="input-small" value="">'
						+ '&nbsp;<input type="text" name="remark" placeholder="备注" class="input-large" value="">'
						+ '</div>';
				$(this).parent().prev().after(str);
			});
			
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/quality/qualityTestroomRemark/">中心试验室备案信息列表</a></li>
		<li class="active"><a href="${ctx}/quality/qualityTestroomRemark/form?id=${qualityTestroomRemark.id}">中心试验室备案信息<shiro:hasPermission name="quality:qualityTestroomRemark:edit">${not empty qualityTestroomRemark.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="quality:qualityTestroomRemark:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="qualityTestroomRemark" action="${ctx}/quality/qualityTestroomRemark/save" method="post" class="form-horizontal">
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
			<label class="control-label">合同段：</label>
			<div class="controls">
				<form:input path="contractSectionLabel" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">母体试验室：</label>
			<div class="controls">
				<form:input path="maternalLaboratory" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资格证书编号：</label>
			<div class="controls">
				<form:input path="qacertificateNum" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权负责人：</label>
			<div class="controls">
				<form:input path="authorizedPerson" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备案人员：</label>
			<form:hidden id="remarkUser" path="remarkUsers" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			<c:if test="${empty userList }">
				<div class="controls">
					<input type="text" name="username" placeholder="姓名" class="input-small" value="">
					<input type="text" name="qualification" placeholder="从业资格" class="input-large" value="">
					<input type="text" name="qCertNum" placeholder="资格证书编号" class="input-small" value="">
					<input type="text" name="remark" placeholder="备注" class="input-large" value="">
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</c:if>
			<c:if test="${not empty userList }">
				<c:forEach items="${userList }" var="item" varStatus="index">
					<div class="controls" style="margin-top: 5px;">
						<input type="text" name="username" placeholder="姓名" class="input-small" value="${item.name }">
						<input type="text" name="qualification" placeholder="从业资格" class="input-large" value="${item.qualifications }">
						<input type="text" name="qCertNum" placeholder="资格证书编号" class="input-small" value="${item.qualificationCertNum }">
						<input type="text" name="remark" placeholder="备注" class="input-large" value="${item.remarks }">
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
			<label class="control-label">授权开展的试验检测项目及参数：</label>
			<div class="controls">
				<form:textarea path="detectionProjectParameter" 
						placeholder="1、土：颗粒级配、液限塑限、最大干密度、最佳含水量、CBR。
2、集料：颗粒级配、压碎值、针片状颗粒含量、含泥量。
3、石料：单轴抗压强度。
4、水泥：凝结时间、安定性、胶砂强度、细度、比表面积。
5、水泥混凝土、砂浆、外加剂：抗压强度、抗折强度、配合比设计、坍落度、混凝土凝结时间。
6、钢筋：抗拉强度、屈服强度、伸长率、冷弯。
7、道路工程：压实度、路面厚度、弯沉、平整度。
8、结构混凝土：强度、混凝土碳化深度。
9、地基基础：地基承载力。
10、隧道：断面轮廓、锚杆拉拔。
	...................."
						htmlEscape="false" rows="12" class="input-xxlarge"/>
				<span class="help-inline" style="margin-top: 185px;"><font color="red">*</font> 试验检测项目及参数根据项目实际情况确定。</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="quality:qualityTestroomRemark:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>