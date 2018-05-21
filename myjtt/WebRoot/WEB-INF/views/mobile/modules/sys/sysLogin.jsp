<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >
<!DOCTYPE html>
<html>
 <head>
    <meta charset="utf-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/font/css/main.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/font/css/font-awesome.min.css">
    <title>陕西省交通厅</title>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries-->
    <!--if lt IE 9
    script(src='${ctxStatic}/font/js/html5shiv.js')
    script(src='${ctxStatic}/font/js/respond.min.js')
    -->
  </head>
<body>
	<section class="material-half-bg">
		<div class="cover"></div>
	</section>
	<section class="login-content">
		<div class="logo">
			<h2>交通安全质量管理</h2>
		</div>
		<div class="login-box">
			<form id="loginForm" class="login-form" action="${ctx }/login"
				method="post" onsubmit="return onCheck();">
				<div class="alert alert-dismissible alert-danger ${empty message ? 'hide' : ''}">
                    <label id="loginError" class="error">${message }</label>
                </div>
				<div class="form-group">
					<label class="control-label">用户名</label> 
					<input name="username" id="username" class="form-control" type="text" placeholder="用户名" autofocus>
				</div>
				<div class="form-group">
					<label class="control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码</label> 
					<input name="password" id="password" class="form-control" type="password" placeholder="密码">
				</div>
				<div class="form-group">
					<div class="utility">
						<div class="animated-checkbox">
							<label class="semibold-text">
							<input type="checkbox"><span class="label-text">记住我</span>
							<input type="hidden" name="mobileLogin" value="true">
							</label>
						</div>
					</div>
				</div>
				<div class="form-group btn-container">
					<button class="btn btn-primary btn-block" type="submit">
						<i class="fa fa-sign-in fa-lg fa-fw"></i>登 录
					</button>
				</div>
			</form>
		</div>
	</section>
</body>
<script src="${ctxStatic}/font/js/jquery-2.1.4.min.js"></script>
<script src="${ctxStatic}/font/js/bootstrap.min.js"></script>
<script src="${ctxStatic}/font/js/plugins/pace.min.js"></script>
<script src="${ctxStatic}/font/js/main.js"></script>
<script type="text/javascript">
	function onCheck() {
		if ( isEmpty($('#username').val()) ) {
			alert("请填写账号");
			return false;
		} else if (isEmpty($('#password').val())) {
			alert("请填写密码");
			return false;
		} else {
			var loginForm = $("#loginForm");
			loginForm.submit();
		}
		
	}
</script>
</html>