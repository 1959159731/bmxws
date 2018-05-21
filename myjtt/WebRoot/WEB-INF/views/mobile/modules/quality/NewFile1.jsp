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
					<li class="active"><a href="/mobile/quality"><i
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
					<div class="weui-cell__bd">
						<div class="weui-uploader">
							<div class="weui-uploader__hd">
								<p class="weui-cells__title">图片上传</p>
								<div class="weui-uploader__info">0/9</div>
							</div>
							<div class="weui-uploader__bd">
								<ul class="weui-uploader__files" id="uploaderFiles">
								</ul>
								<div class="weui-uploader__input-box">
									<input id="uploaderInput"
										class="weui-uploader__input zjxfjs_file" type="file"
										accept="image/*" multiple="">
								</div>
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
				
				<div class="weui-gallery" id="gallery">
					<span class="weui-gallery__img" id="galleryImg"></span>
					<div class="weui-gallery__opr">
						<a href="javascript:" class="weui-gallery__del"> <i
							class="weui-icon-delete weui-icon_gallery-delete"></i>
						</a>
					</div>
				</div>
				
				<div class="page__bd page__bd_spacing">
					<a href="javascript:void();" class="weui-btn weui-btn_primary" id="saveBtn">确 定</a>
				</div>
				
			</div>

		</div>
	</div>
	</div>
	<!-- Javascripts-->
	<script src="${ctxStatic}/font/js/jquery-2.1.4.min.js"></script>
	<script src="${ctxStatic}/font/js/bootstrap.min.js"></script>
	<script src="${ctxStatic}/font/js/plugins/pace.min.js"></script>
	<script src="${ctxStatic}/font/js/main.js"></script>
	<script type="text/javascript" src="${ctxStatic}/weui/js/weui.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/weui/js/jquery-weui.min.js"></script>
	<script type="text/javascript">
	
        $(function() {
        	var uploadCount = 0;
            var tmpl = '<li class="weui-uploader__file" style="background-image:url(#url#)"></li>',
                $gallery = $("#gallery"),
                $galleryImg = $("#galleryImg"),
                $uploaderInput = $("#uploaderInput"),
                $uploaderFiles = $("#uploaderFiles");
            var fileArr = [];
            
            $uploaderInput.on("change", function(e) {
                var src, url = window.URL || window.webkitURL || window.mozURL,
                    files = e.target.files;
                
                if(uploadCount >= 9){
                	$.alert("最多只可以上传9张照片。");
                	return;
                }
                
                for(var i = 0, len = files.length; i < len; ++i) {
                    var file = files[i];
                    fileArr.push(file);
                    if(url) {
                        src = url.createObjectURL(file);
                    } else {
                        src = e.target.result;
                    }
                    $uploaderFiles.append($(tmpl.replace('#url#', src)));
                    uploadCount += 1;
                    $('.weui-uploader__info').text(uploadCount + '/' + 9);
                }
            });
            
            var index; //第几张图片
            $uploaderFiles.on("click", "li", function() {
                index = $(this).index();
                $galleryImg.attr("style", this.getAttribute("style"));
                $gallery.fadeIn(100);
            });
            
            $gallery.on("click", function() {
                $gallery.fadeOut(100);
            });
            
            //删除图片  删除图片的代码也贴出来。
            $(".weui-gallery__del").click(function() { //这部分刚才放错地方了，放到$(function(){})外面去了
                $uploaderFiles.find("li").eq(index).remove();
                uploadCount -= 1;
                $('.weui-uploader__info').text(uploadCount + '/' + 9);
                fileArr.splice(index, 1);
            });
            
            $("#saveBtn").on("click", function(){
            	uploadFunction(fileArr);
            });
            
            
            var uploadFunction = function(fileArr){
            	for(var i = 0, len = fileArr.length; i < len; ++i) {
                    var file = fileArr[i];
                    var reader = new FileReader();
                 	
                    reader.onload = function (e) {
                        var img = new Image();
                        img.onload = function () {
                            var square = 700;  
                            var canvas = document.createElement('canvas');
                            var ctx = canvas.getContext('2d');
                            // ctx.clearRect(0, 0, square, square); 
                            // 设置 canvas 的宽度和高度
                            var imageWidth;  
				            var imageHeight;  
				            var offsetX = 0;  
				            var offsetY = 0;
							if (img.width > img.height) {
								imageWidth = Math.round(square * img.width / img.height);
								imageHeight = square;
								offsetX = -Math.round((imageWidth - square) / 2);
							} else {
								imageHeight = Math.round(square * img.height / img.width);
								imageWidth = square;
								offsetY = -Math.round((imageHeight - square) / 2);
							}
							ctx.drawImage(img, 0, 0, 75, 75);
							var base64 = canvas.toDataURL('image/jpeg');
							Console.log(base64);
						};
						img.src = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            }
        });
    </script>
</body>
</html>