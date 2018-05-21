<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目基本信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					if(!/^1[0-9]{10}$/.test($("#checkTelphoneId").val())){
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
			
			
			$("#checkTelphoneId").on("blur", function(){
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
		<li><a href="${ctx}/project/proSimpleInfo/">项目基本信息列表</a></li>
		<li class="active"><a href="${ctx}/project/proSimpleInfo/form?id=${proSimpleInfo.id}">项目基本信息<shiro:hasPermission name="project:proSimpleInfo:edit">${not empty proSimpleInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="project:proSimpleInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="proSimpleInfo" action="${ctx}/project/proSimpleInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<td>项目名称</td>
					<td colspan="2"><form:input path="projectName" htmlEscape="false" maxlength="64" class="input-large required"/></td>
					<td>项目简称</td>
					<td><form:input path="projectSimpleName" htmlEscape="false" maxlength="64" class="input-small required"/></td>
					<td>项目类型</td>
					<td colspan="2"><form:select path="projectType" class="input-large required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('project_simple_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>现场管理机构</td>
					<td colspan="2"><form:input path="siteManagementAgencies" htmlEscape="false" maxlength="64" class="input-large required"/></td>
					<td>设计单位</td>
					<td colspan="2"><form:input path="designUnit" htmlEscape="false" maxlength="64" class="input-large required"/></td>
					<td>项目法人</td>
					<td><form:input path="projectLegal" htmlEscape="false" maxlength="64" class="input-small required"/></td>
				</tr>
				<tr>
					<td>建设里程（km）</td>
					<td><form:input path="buildMileage" htmlEscape="false" class="input-small required number"/></td>
					<td>设计时速（km）</td>
					<td><form:input path="designSpeed" htmlEscape="false" class="input-small required number"/></td>
					<td>路基宽度（m）</td>
					<td><form:input path="subgradeWidth" htmlEscape="false" class="input-small required number"/></td>
					<td>桥隧比例（%）</td>
					<td><form:input path="bridgeRatio" htmlEscape="false" class="input-small required number"/></td>
				</tr>
				<tr>
					<td>工程概算（万元）</td>
					<td><form:input path="projectEstimate" htmlEscape="false" class="input-small required number"/></td>
					<td>建安费（万元）</td>
					<td><form:input path="constructionResetFee" htmlEscape="false" class="input-small required number"/></td>
					<td>办理监督手续时间</td>
					<td><input name="appSupervisionTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width: 120px;"
								value="<fmt:formatDate value="${proSimpleInfo.appSupervisionTime}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></td>
					<td>监督管理受理时间</td>
					<td><input name="manSupervisionTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width: 120px;"
								value="<fmt:formatDate value="${proSimpleInfo.manSupervisionTime}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></td>
				</tr>
				<tr>
					<td>批准工期（月）</td>
					<td><form:input path="approveDuration" htmlEscape="false" class="input-small required number"/></td>
					<td>合同工期（月）</td>
					<td><form:input path="contractDuration" htmlEscape="false" class="input-small required number"/></td>
					<td>(拟)开工时间</td>
					<td><input name="proposedStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width: 120px;"
								value="<fmt:formatDate value="${proSimpleInfo.proposedStartTime}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></td>
					<td>(拟)交工时间</td>
					<td><input name="proposedDeliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width: 120px;"
								value="<fmt:formatDate value="${proSimpleInfo.proposedDeliveryTime}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></td>
				</tr>
			</tbody>
		</table>
		<hr>
		
		<!------------------------------------------- 项目基本信息-审批信息 START ------------------------------------------------>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th style="width: 10%;"></th>
					<th style="width: 20%;">审批单位</th>
					<th style="width: 20%;">批准文号</th>
					<th style="width: 20%;">批准时间</th>
					<th style="width: 30%;">附件</th>
				</tr>
			</thead>
			<tbody>
					<tr>
						<td>
							项目立项
						</td>
						<td>
							<form:input path="proSimpleApprove.approvalCompany" htmlEscape="false" maxlength="64" class="input-large required"/>
						</td>
						<td>
							<form:input path="proSimpleApprove.approvalNum" htmlEscape="false" maxlength="64" class="input-large required"/>
						</td>
						<td>
							<input name="proSimpleApprove.approvalTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
									value="<fmt:formatDate value="${proSimpleInfo.proSimpleApprove.approvalTime}" pattern="yyyy-MM-dd"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</td>
						<td>
							<form:hidden id="approvalFilename" path="proSimpleApprove.approvalFilepath" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
							<sys:ckfinder input="approvalFilename" type="files" uploadPath="/file" selectMultiple="true" maxWidth="100" maxHeight="100"/>
						</td>
					</tr>
					<tr>
						<td>
							工可批复
						</td>
						<td>
							<form:input path="proSimpleApprove.workersApprovalCompany" htmlEscape="false" maxlength="64" class="input-large required"/>
						</td>
						<td>
							<form:input path="proSimpleApprove.workersApprovalNum" htmlEscape="false" maxlength="64" class="input-large required"/>
						</td>
						<td>
							<input name="proSimpleApprove.workersApprovalTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
									value="<fmt:formatDate value="${proSimpleInfo.proSimpleApprove.workersApprovalTime}" pattern="yyyy-MM-dd"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</td>
						<td>
							<form:hidden id="workersApprovalFilename" path="proSimpleApprove.workersApprovalFilepath" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
							<sys:ckfinder input="workersApprovalFilename" type="files" uploadPath="/file" selectMultiple="true" maxWidth="100" maxHeight="100"/>
						</td>
					</tr>
					<tr>
						<td>
							初步设计审查
						</td>
						<td>
							<form:input path="proSimpleApprove.designApprovalCompany" htmlEscape="false" maxlength="64" class="input-large required"/>
						</td>
						<td>
							<form:input path="proSimpleApprove.designApprovalNum" htmlEscape="false" maxlength="64" class="input-large required"/>
						</td>
						<td>
							<input name="proSimpleApprove.designApprovalTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
									value="<fmt:formatDate value="${proSimpleInfo.proSimpleApprove.designApprovalTime}" pattern="yyyy-MM-dd"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</td>
						<td>
							<form:hidden id="designApprovalFilename" path="proSimpleApprove.designApprovalFilepath" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
							<sys:ckfinder input="designApprovalFilename" type="files" uploadPath="/file" selectMultiple="true" maxWidth="100" maxHeight="100"/>
						</td>
					</tr>
					<tr>
						<td>
							施工图设计批复
						</td>
						<td>
							<form:input path="proSimpleApprove.constructionDesignApprovalUnit" htmlEscape="false" maxlength="64" class="input-large required"/>
						</td>
						<td>
							<form:input path="proSimpleApprove.constructionDesignApprovalNum" htmlEscape="false" maxlength="64" class="input-large required"/>
						</td>
						<td>
							<input name="proSimpleApprove.constructionDesignApprovalTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
								value="<fmt:formatDate value="${proSimpleInfo.proSimpleApprove.constructionDesignApprovalTime}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</td>
						<td>
							<form:hidden id="constructionDesignFilename" path="proSimpleApprove.constructionDesignApprovalFilepath" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
							<sys:ckfinder input="constructionDesignFilename" type="files" uploadPath="/file" selectMultiple="true" maxWidth="100" maxHeight="100"/>
						</td>
					</tr>
			</tbody>
		</table>
		<hr>
		<!------------------------------------------- 项目基本信息-审批信息 END ------------------------------------------------>
		
		
		<!------------------------------------------- 项目桥梁、隧道信息 START ------------------------------------------------>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th colspan="4">桥梁工程</th>
					<th colspan="4">隧道工程</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>特大桥（座）</td>
					<td><form:input path="proSimpleBridgeTunnel.extraLargeBridgeNum" htmlEscape="false" maxlength="11" class="input-small required digits"/></td>
					<td>累计长度（m）</td>
					<td><form:input path="proSimpleBridgeTunnel.extraLargeBridgeLength" htmlEscape="false" class="input-small required number"/></td>
					<td>特长隧道（座）</td>
					<td><form:input path="proSimpleBridgeTunnel.extraLongTunnelNum" htmlEscape="false" maxlength="11" class="input-small required digits"/></td>
					<td>累计长度（m）</td>
					<td><form:input path="proSimpleBridgeTunnel.extraLongTunnelLength" htmlEscape="false" class="input-small required number"/></td>
				</tr>
				
				<tr>
					<td>大桥（座）</td>
					<td><form:input path="proSimpleBridgeTunnel.largeBridgeNum" htmlEscape="false" maxlength="11" class="input-small required digits"/></td>
					<td>累计长度（m）</td>
					<td><form:input path="proSimpleBridgeTunnel.largeBridgeLength" htmlEscape="false" class="input-small required number"/></td>
					<td>长隧道（座）</td>
					<td><form:input path="proSimpleBridgeTunnel.longTunnelNum" htmlEscape="false" maxlength="11" class="input-small required digits"/></td>
					<td>累计长度（m）</td>
					<td><form:input path="proSimpleBridgeTunnel.longTunnelLength" htmlEscape="false" class="input-small required number"/></td>
				</tr>
				
				<tr>
					<td>中桥（座）</td>
					<td><form:input path="proSimpleBridgeTunnel.mediumBridgeNum" htmlEscape="false" maxlength="11" class="input-small required digits"/></td>
					<td>累计长度（m）</td>
					<td><form:input path="proSimpleBridgeTunnel.mediumBridgeLength" htmlEscape="false" class="input-small required number"/></td>
					<td>中隧道（座）</td>
					<td><form:input path="proSimpleBridgeTunnel.mediumTunnelNum" htmlEscape="false" maxlength="11" class="input-small required digits"/></td>
					<td>累计长度（m）</td>
					<td><form:input path="proSimpleBridgeTunnel.mediumTunnelLength" htmlEscape="false" class="input-small required number"/></td>
				</tr>
				
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>短隧道（座）</td>
					<td><form:input path="proSimpleBridgeTunnel.shortTunnelNum" htmlEscape="false" maxlength="11" class="input-small required digits"/></td>
					<td>累计长度（m）</td>
					<td><form:input path="proSimpleBridgeTunnel.shortTunnelLength" htmlEscape="false" class="input-small required number"/></td>
				</tr>
			</tbody>
		</table>
		<hr>
		<!------------------------------------------- 项目桥梁、隧道信息 END ------------------------------------------------>
		
		
		
		
		<!------------------------------------------- 项目施工、监理单位信息 START ------------------------------------------------>
		<div class="control-group">
			<div class="span6">
				<label class="control-label">施工合同段（个）：</label>
				<div class="controls">
					<form:input path="proSimpleSupervision.contractSectionNum" htmlEscape="false" maxlength="11" class="input-large required digits"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="span6">
				<label class="control-label">监理模式：</label>
				<div class="controls">
					<form:input path="proSimpleSupervision.supervisionMode" htmlEscape="false" maxlength="64" class="input-large required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="span6">
				<label class="control-label">监理合同段（个）：</label>
				<div class="controls">
					<form:input path="proSimpleSupervision.supervisionSectionNum" htmlEscape="false" maxlength="11" class="input-large required digits"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="span6">
				<label class="control-label">项目联系人：</label>
				<div class="controls">
					<form:input path="proSimpleSupervision.projectContactUsername" htmlEscape="false" maxlength="64" class="input-large required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="span6">
				<label class="control-label">联系电话：</label>
				<div class="controls">
					<form:input id="checkTelphoneId" path="proSimpleSupervision.projectContactPhone" htmlEscape="false" maxlength="32" class="input-large required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</div>
		<!------------------------------------------- 项目施工、监理单位信息 END ------------------------------------------------>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="project:proSimpleInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>