<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css" />
<script src="js/jquery.js"></script>
<style>
	body{
		background-image: url(images/cat.jpg);
	}
	#container .form {
		margin-top:200px;
		margin-left:500px;
	}
	#container .form label,#container .form .error{
		display:inline;
	}
	#username,#password{
		display:inline;
		width:200px;
	}
	#verifyCode{
		display:inline;
		width:125px;
	}
	#container .verifyCode{
		display:inline;
		cursor:pointer;
	}
	#container .form .error{
		color:red;
	}
</style>
<script>
	$().ready(function(){
		if(window != top) {
	        top.location.href = location.href;
	    }   
		$("#username").click(function(){
			$(".error").css("display","none");
		});
		$("#regist").click(function(){
			window.location.href="user.do?method=showRegist";
		});
		$(".verifyCode img").click(function(){
			$(this).attr("src","user.do?method=createVerify&"+new Date().getTime());
		});
	})
</script>
</head>
<body >
	<div id="container">
			<form class="form" method="post" action="user.do">
				<input type="hidden" name="method" value="login"/>
				<div class="form-group">
					<label for="user">账&#12288;号：</label>
					<input type="text" class="form-control" id="username" name="username" autocomplete="off" value="${username }"/>
					<div class="error">
						<c:if test="${not empty noName_error }">${noName_error }</c:if>
						<c:if test="${not empty username_error }">${username_error }</c:if>
					</div>
				</div>
				<div class="form-group">
					<label for="password">密&#12288;码：</label>
					<input type="password" class="form-control" id="password" name="password" autocomplete="off" />
					<div class="error">
						<c:if test="${not empty noPsw_error }">${noPsw_error }</c:if>
						<c:if test="${not empty password_error }">${password_error }</c:if>
					</div>
				</div>
				<div class="form-group">
					<label for="verifyCode">验证码：</label>
					<input type="text" class="form-control" id="verifyCode" name="verifyCode" autocomplete="off" />
					<div class="verifyCode">
						<img src="user.do?method=createVerify">
					</div>
					<div class="error">
						<c:if test="${not empty verifyCode_error }">${verifyCode_error }</c:if>
						<c:if test="${not empty noVC_error }">${noVC_error }</c:if>
					</div>
				</div>
				<button type="submit" id="login" class="btn btn-primary">登录</button>
				<button type="button" id="regist" class="btn btn-primary">注册</button>
			</form>
		</div>
</body>
</html>