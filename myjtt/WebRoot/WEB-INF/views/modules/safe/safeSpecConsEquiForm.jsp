<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专用设备管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
		$(document).ready(function() {
			
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var _imgPath = $("#nameImage").val();
					if(!_imgPath){
						alertx("请上传证书");
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
			
			
			
			/* model 初始化加载数据  */
			$('#myModal').on('show.bs.modal', function (event) {
				var modal = $(this);
				$.ajax({
			        type: 'POST',
			        url : '${ctx}/safe/safeOpertionUserinfo/getOpertionUser',
			        data: {
			        	opertionUserType: '2'
			        },
	        		success : function(data) {
	        			if(data != null){
	        				var innerHtml = ""
		        	    	$.each(data, function(index, item){
		        	    		innerHtml += '<tr><td>' 
	        	    					+ '<input type="checkbox" username="' + item.name + '" name="checkbox" value="' + item.id + '">'
	        	    					+ '</td><td>'
	        	    					+ item.name
	        	    					+ '</td><td>'
	        	    					+ item.cid
	        	    					+ '</td><td>'
	        	    					+ item.gender
	        	    					+ '</td><td>'
	        	    					+ item.certificateNo
	        	    					+ '</td><td>'
	        	    					+ item.certificateValid
	        	    					+ '</td><td>'
	        	    					+ item.workSite
	        	    					+ '</td></tr>';
		        			});
	        				modal.find('#listTable').html(innerHtml);
	        			}
			        }
			    });
			});
			
			
			/* 全选、取消全选   */
			/* $("#checkAll").on('click', function(){ 
			    if ($("#checkAll").prop("checked")) { 
			        $("input[type='checkbox']").prop("checked",true);	//全选
			    } else { 
			        $("input[type='checkbox']").prop("checked",false);	//取消全选     
			    }  
			}); */
			
			
			
			// 添加按钮
			$("#addUser").on('click', function (){
				var userId = "";
				var username = "";
				var count = 0;
				$("input[type='checkbox'][name='checkbox']:checkbox:checked").each(function(){ 
					userId += $(this).val() + "###";
					username += $(this).attr("username") + ",";
					count++;
				});
				if(count > 2){
					alertx("最多请选择2个操作人员");
					return;
				}
				$("#operatorUser1").val( userId.split("###")[0] );
				$("#operatorUsersShow").val( username.substring(0, username.length - 1) );
				$("#operatorUser2").val( userId.split("###")[1] );
				$('#myModal').modal('hide');
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
			
			
			$("#btnSubmit").on("click", function(){
				$("#inputForm").attr("action", "${ctx}/safe/safeSpecConsEqui/save");
			});
			
			
			$("#exitSubmit").on("click", function(){
				$("#inputForm").attr("action", "${ctx}/safe/safeSpecConsEqui/exit");
			});
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/safe/safeSpecConsEqui/">专用设备列表</a></li>
		<li class="active"><a href="${ctx}/safe/safeSpecConsEqui/form?id=${safeSpecConsEqui.id}">专用设备<shiro:hasPermission name="safe:safeSpecConsEqui:edit">${not empty safeSpecConsEqui.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="safe:safeSpecConsEqui:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="safeSpecConsEqui" action="" method="post" class="form-horizontal">
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
			<label class="control-label">管理编号：</label>
			<div class="controls">
				<form:input path="managementNumber" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格型号：</label>
			<div class="controls">
				<form:input path="specification" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产厂家：</label>
			<div class="controls">
				<form:input path="manufacturer" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备来源：</label>
			<div class="controls">
				<form:select path="equipmentSource" class="input-xlarge required" style="width: 285px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('safe_equipment_source')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计算书符合报告出具单位：</label>
			<div class="controls">
				<form:input path="reportCompany" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计算书符合报告出具时间：</label>
			<div class="controls">
				<input name="reportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${safeSpecConsEqui.productionDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产日期：</label>
			<div class="controls">
				<input name="productionDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${safeSpecConsEqui.productionDate}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进场时间：</label>
			<div class="controls">
				<input name="approachTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${safeSpecConsEqui.approachTime}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证书：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作人员：</label>
			<div class="controls">
				<form:hidden id="operatorUser1" path="operatorUser.id" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<form:hidden id="operatorUser2" path="operatorUserOther.id" htmlEscape="false" maxlength="255" class="input-xlarge required" value="${operatorUser }"/>
				<input id="operatorUsersShow"  htmlEscape="false" readonly="readonly" maxlength="255" 
						class="input-xlarge required" value="${operatorUser }"/>
				<a href="#myModal" id="modelReady" role="button" class="btn btn-success" data-toggle="modal">添加人员</a>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${empty safeSpecConsEqui.exitTime }">
				<shiro:hasPermission name="safe:safeSpecConsEqui:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
				<c:if test="${not empty safeSpecConsEqui.id }">
					<shiro:hasPermission name="safe:safeSpecConsEqui:exit">
					<input id="exitSubmit" class="btn btn-primary" type="submit" value="退场"/>&nbsp;</shiro:hasPermission>
				</c:if>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	
	
	<!-------------------------------------------- Modal Start ---------------------------------------------------------------->
	<div id="myModal" class="modal hide fade" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="width: 800px; left: 30%;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">专用设备操作人员添加</h3>
		</div>
		<div class="modal-body">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th><!-- <input type="checkbox" id="checkAll" value=""> --></th>
						<th>姓名</th>
						<th>身份证号</th>
						<th>性别</th>
						<th>执业证书编号</th>
						<th>证书有效期</th>
						<th>作业工点或位置</th>
					</tr>
				</thead>
				<tbody id="listTable">
				
				</tbody>
			</table>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
			<button class="btn btn-primary" id="addUser">添加</button>
		</div>
	</div>
	<!-------------------------------------------- Modal End ---------------------------------------------------------------->
	
	
	
</body>
</html>