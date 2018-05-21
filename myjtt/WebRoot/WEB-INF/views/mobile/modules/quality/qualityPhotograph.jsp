<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- CSS-->
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/font/css/main.css">
<!-- Font-icon css-->
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/font/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/weui/css/weui.min.css">
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/weui/css/jquery-weui.min.css">
<title>陕西省交通厅</title>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries-->
<!--if lt IE 9
	script(src='${ctxStatic}/font/js/html5shiv.js')
	script(src='${ctxStatic}/font/js/respond.min.js')
-->
<style>
        * {
            margin: 0;
            padding: 0;
        }
        /*图片上传*/
        
        .container {
            width: 100%;
            height: 100%;
            overflow: auto;
            clear: both;
        }
        
        .z_photo {
            width: 5rem;
            height: 5rem;
            padding: 0.3rem;
            overflow: auto;
            clear: both;
            margin: 1rem auto;
            border: 1px solid #555;
        }
        
        .z_photo img {
            width: 1rem;
            height: 1rem;
        }
        
        .z_addImg {
            float: left;
            margin-right: 0.2rem;
        }
        
        .z_file {
            width: 1rem;
            height: 1rem;
            background: url(${ctxStatic}/weui/img/z_add.png) no-repeat;
            background-size: 100% 100%;
            float: left;
            margin-right: 0.2rem;
        }
        
        .z_file input::-webkit-file-upload-button {
            width: 1rem;
            height: 1rem;
            border: none;
            position: absolute;
            outline: 0;
            opacity: 0;
        }
        
        .z_file input#file {
            display: block;
            width: auto;
            border: 0;
            vertical-align: middle;
        }
        /*遮罩层*/
        
        .z_mask {
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, .5);
            position: fixed;
            top: 0;
            left: 0;
            z-index: 999;
            display: none;
        }
        
        .z_alert {
            width: 3rem;
            height: 2rem;
            border-radius: .2rem;
            background: #fff;
            font-size: .24rem;
            text-align: center;
            position: absolute;
            left: 50%;
            top: 50%;
            margin-left: -1.5rem;
            margin-top: -2rem;
        }
        
        .z_alert p:nth-child(1) {
            line-height: 1.5rem;
        }
        
        .z_alert p:nth-child(2) span {
            display: inline-block;
            width: 49%;
            height: .5rem;
            line-height: .5rem;
            float: left;
            border-top: 1px solid #ddd;
        }
        
        .z_cancel {
            border-right: 1px solid #ddd;
        }

    </style>
</head>
<body class="sidebar-mini fixed">
	<div class="wrapper">
		<!-- Navbar-->
		<header class="main-header hidden-print">
			<a class="logo" href="index.html">质量安全</a>
			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
				<a class="sidebar-toggle" href="#" data-toggle="offcanvas"></a>
				<!-- Navbar Right Menu-->
				<div class="navbar-custom-menu">
					<ul class="top-nav">
						<!-- User Menu-->
						<li class="dropdown"><a class="dropdown-toggle" href="#"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"> <i class="fa fa-user fa-lg"></i>
						</a>
							<ul class="dropdown-menu settings-menu">
								<li><a href="${ctx}/logout"><i
										class="fa fa-sign-out fa-lg"></i> 退出</a></li>
							</ul></li>
					</ul>
				</div>
			</nav>
		</header>

		<!-- Side-Nav-->
		<aside class="main-sidebar hidden-print">
			<section class="sidebar">
				<div class="user-panel">
					<div class="pull-left image">
						<img class="img-circle" src="${fns:getUser().photo}"
							alt="User Image">
					</div>
					<div class="pull-left info">
						<p>${fns:getUser().name}</p>
					</div>
				</div>
				<!-- Sidebar Menu-->
				<ul class="sidebar-menu">
					<li><a href="${ctx}/mobile/index"><i
							class="fa fa-dashboard"></i><span>项目统计</span></a></li>
				</ul>
				<ul class="sidebar-menu">
					<li class="active"><a href="${ctx}/mobile/quality"><i
							class="fa fa-dashboard"></i><span>质量隐患</span></a></li>
				</ul>
			</section>
		</aside>
		<div class="content-wrapper">
			<div class="page-title">
				<div>
					<ul class="breadcrumb">
						<li><i class="fa fa-home fa-lg"></i></li>
						<li><a href="${ctx}/mobile/quality">质量隐患</a></li>
					</ul>
				</div>
			</div>

			<div class="weui-cells weui-cells_form">
			<form action="${ctx}/mobile/quality/AddImage" method="POST">
			
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-cells__title">项目名称</label>
					</div>
					<div class="weui-cell__bd">
						<select class="weui-select">
							<option selected value="0">请选择</option>
							<option value="1">微信号</option>
							<option value="2">QQ号</option>
							<option value="3">Email</option>
						</select>
					</div>
				</div>

				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-cells__title">项目标段</label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input" type="text" placeholder="请输入项目标段">
					</div>
				</div>

				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-cells__title">隐患标题</label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input" type="text" placeholder="请输入项目隐患标题">
					</div>
				</div>

				<div class="weui-cell">
					<div class="container">
						<!--    照片添加    -->
						<div class="z_photo">
							<div class="z_file">
								<input type="file" name="file" id="file" value=""
									accept="image/*" multiple
									onchange="imgChange('z_photo','z_file');" />

							</div>

						</div>

						<!--遮罩层-->
						<div class="z_mask">
							<!--弹出框-->
							<div class="z_alert">
								<p>确定要删除这张图片吗？</p>
								<p>
									<span class="z_cancel">取消</span> <span class="z_sure">确定</span>
								</p>
							</div>
						</div>
					</div>
				</div>

				<div class="weui-cells__title">项目隐患内容详细描述</div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea class="weui-textarea" rows="3"></textarea>
					</div>
				</div>
				
				<div class="page__bd page__bd_spacing">
					<button class="weui-btn weui-btn_primary" type="submit">确 定</button>
				</div>
				</form>
			</div>

		</div>
	</div>
	</div>
	<!-- Javascripts-->
	<script src="${ctxStatic}/font/js/jquery-2.1.4.min.js"></script>
	<script src="${ctxStatic}/font/js/bootstrap.min.js"></script>
	<script src="${ctxStatic}/font/js/plugins/pace.min.js"></script>
	<script src="${ctxStatic}/font/js/main.js"></script>
	<script type="text/javascript">
	
	//px转换为rem
    (function(doc, win) {
        var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function() {
                var clientWidth = docEl.clientWidth;
                if (!clientWidth) return;
                if (clientWidth >= 640) {
                    docEl.style.fontSize = '100px';
                } else {
                    docEl.style.fontSize = 100 * (clientWidth / 640) + 'px';
                }
            };

        if (!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window);

    function imgChange(obj1, obj2) {
        //获取点击的文本框
        var file = document.getElementById("file");
        console.log(file);
        //存放图片的父级元素
        var imgContainer = document.getElementsByClassName(obj1)[0];
        //获取的图片文件
        var fileList = file.files;
        console.log(fileList);
        //文本框的父级元素
        var input = document.getElementsByClassName(obj2)[0];
        var imgArr = [];
        //遍历获取到得图片文件
        for (var i = 0; i < fileList.length; i++) {
            var imgUrl = window.URL.createObjectURL(file.files[i]);
            imgArr.push(imgUrl);
            var img = document.createElement("img");
            img.setAttribute("src", imgArr[i]);
            var imgAdd = document.createElement("div");
            imgAdd.setAttribute("class", "z_addImg");
            imgAdd.appendChild(img);
            imgContainer.appendChild(imgAdd);
        };
        imgRemove();
    };

    function imgRemove() {
        var imgList = document.getElementsByClassName("z_addImg");
        var mask = document.getElementsByClassName("z_mask")[0];
        var cancel = document.getElementsByClassName("z_cancel")[0];
        var sure = document.getElementsByClassName("z_sure")[0];
        for (var j = 0; j < imgList.length; j++) {
            imgList[j].index = j;
            imgList[j].onclick = function() {
                var t = this;
                mask.style.display = "block";
                cancel.onclick = function() {
                    mask.style.display = "none";
                };
                sure.onclick = function() {
                    mask.style.display = "none";
                    t.style.display = "none";
                };

            }
        };
    };
        
    </script>
</body>
</html>