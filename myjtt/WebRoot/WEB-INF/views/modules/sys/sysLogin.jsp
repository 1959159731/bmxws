<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<link href="../static/bootstrap/2.3.1/css_cerulean/img/favicon.ico" mce_href="favicon.ico" rel="shortcut icon" type="image/x-icon" />
	<style type="text/css"> 
	body{
	border:0 ;
	margin: 0;
	padding: 0;
	min-width: 1100px;
	}
.clear{
	clear: both;
}
a {
	background-color: transparent;
	text-decoration: none;
}
a:active,
a:hover {
	outline: 0;
	text-decoration: none;
	background-color: transparent;
}
ul,li {
	margin: 0;
	padding: 0;
	list-style: none;
}
button[disabled],
html input[disabled] {
	cursor: default;
}
button::-moz-focus-inner,
input::-moz-focus-inner {
	border: 0;
	padding: 0;
}
#wapper{
	width: 100%;
	height: 100%;

}
.top{
	width: 100%;
	background-color: #FFFFFF;
	padding-top:30px;
	padding-bottom:20px;
}
.top_inner{
	width: 1100px;
	margin: 0 auto;
	padding: 0px 20px 0px 10px;
}
.center{
	background-image:url(../static/images/login_bg_bak.jpg);
	background-repeat:no-repeat ;
	background-position: center top;
	background-color:#3467a8;
	height: 450px;
}
.center_inner{
	width: 1100px;
	margin: 0 auto;
}
.login{
	float: right;
	width:390px;
	height: 362px;
	border-radius:10px ;
	margin-top: 25px;
	position: relative;
}
.login_top{
	position: absolute;
	left:44px;
}
.login_inner{
	display: inline-block;
	background-image: url(../static/images/login_inner_bg.png);
	background-repeat:no-repeat ;
	border-radius:8px ;
	padding: 90px 43px 40px 43px;
	margin:0;
}
.login_inner li{
	width: 100%;
	padding: 10px 0;
}
.login_inner li input{
	width: 264px;
	height:40px;
	border-radius:4px;
	border: 1px solid #CCCCCC;
	padding: 0px 2px 0px 34px;
	background-repeat:no-repeat ;
	font-size: 14px;
	color: #888888;
	background-color:#ffffff;
}
input.user{
	background-image:url(../static/images/login_icon_03.png);
}
input.password{
	background-image: url(../static/images/login_icon_06.png);
}
.checkbox{
	display: inline-block;
	padding: 0;
	margin: 0;
	padding: 10px 10px 10px 0;
	margin:4px;
	font-size: 14px;
	color: #a3bdda;
}
.buttun{
	width: 100%;
}
.login_inner .login_button{
	display: inline-block;
	width: 304px;
	padding: 10px;
	color: #FFFFFF;
	font-family: "微软雅黑";
	font-size: 18px;
	background-color: #ff8400;
	border-radius:4px ;
	letter-spacing: 6px;
	border: 0;
}
.bottom{
	padding:15px 0;
	line-height:20px;
}
.bottom p{
	width: 100%;
	text-align: center;
	font-size: 12px;
	font-family: "微软雅黑";
	color: #858585;
}
.alert{position:relative;width:252px;margin:0 auto;*padding-bottom:0px;}
.radio input[type="radio"], .checkbox input[type="checkbox"]{
	float:none;
	margin:0;
}
.alert	{
	position:absolute;
	top:66px;}
p{
	margin:0;
}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			/**
			 * 检索回车监听
			 */
			 $(document).keyup(function(e) {
						if (e.keyCode == 13)
							$("#loginForm").submit();
					});
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alertx('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
 	 <div id="wapper">
			<div class="top">
				<div class="top_inner"><img  src="../static/images/logo_03-1.png"/></div>
			</div>
			<div class="center">
				<div class="center_inner">
				<div class="login">
					<div class="login_top">
						<img src="../static/images/login_top_03.png"/>
					</div>
					<ul class="login_inner">
					<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
					<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
						<label id="loginError" class="error">${message}</label>
					</div>
						<li><input class="user" type="text" id="username" name="username"  placeholder="输入用户名"/></li>
						<li><input class="password" type="password" id="password" name="password" value="123456" placeholder="输入密码" /></li>
						<c:if test="${isValidateCodeLogin}"><div class="validateCode">
						<label class="input-label mid" for="validateCode">验证码</label>
						<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
					</div></c:if>
					<div class="checkbox"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> <span>记住我（公共场所慎用）</span></div>
						<li><button class="login_button" type="submit">登录</button></li>
						</form>
					</ul>
					
					</div>
				</div>
			</div>
			<div class="bottom" style="display: black;">
				<p style="color:red">建议使用Chrome浏览器获得最佳体验</p>
				<p>Copyright &copy; ${fns:getConfig('copyrightYear')} <a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('systemName')}</a> - Powered By <a href="${fns:getConfig('productWeb')}" target="_blank">${fns:getConfig('productName')}</a> ${fns:getConfig('version')}</p>
			</div>
		 
		</div>
		<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>  
</body>
</html>