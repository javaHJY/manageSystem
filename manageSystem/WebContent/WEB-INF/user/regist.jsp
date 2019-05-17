<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册</title>
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
	#username,#password,#repeat{
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
	.error{
		color:red;
	}
	.repeat_error{
		color: red;
	}
</style>
<script>
	$().ready(function(){
		$("#username").click(function(){
			$(".error").css("display","none");
			$(".repeat_error").css("display","none");
		});
		$("#regist").click(function(){
			if($("#repeat").val()!=$("#password").val()){
				$("#password").val("");
				$("#repeat").val("");
				$(".repeat_error").show();
				$(".repeat_error").css("display","inline");
				return false;
			}
		});
		$(".verifyCode img").click(function(){
			$(this).attr("src","user.do?method=createVerify&"+new Date().getTime());
		})
	})
</script>
</head>
<body>
	<div id="container">
			<form class="form" method="post" action="user.do" >
				<input type="hidden" name="method" value="regist"/>
				<div class="form-group">
					<label for="user">账&#12288;	&nbsp;&nbsp;&nbsp;号：</label>
					<input type="text" class="form-control" id="username" name="username" autocomplete="off"/>
					<div class="error">
						<c:if test="${not empty nullName_error }">${nullName_error }</c:if>
						<c:if test="${not empty regist_error }">${regist_error }</c:if>
						<c:if test="${not empty regist_fail }">${regist_fail }</c:if>
					</div>
				</div>
				<div class="form-group">
					<label for="password">密&#12288;	&nbsp;&nbsp;&nbsp;码：</label>
					<input type="password" class="form-control" id="password" name="password" autocomplete="off" />
					<div class="error">
						<c:if test="${not empty nullPassword_error }">${nullPassword_error }</c:if>
					</div>
				</div>
				<div class="form-group">
					<label for="password">确认密码：</label>
					<input type="password" class="form-control" id="repeat" autocomplete="off" />
					<div class="repeat_error" hidden="hidden">密码不一致</div>
				</div>
				<div class="form-group">
					<label for="verifyCode">验&nbsp;&nbsp;证&nbsp;&nbsp;码：</label>
					<input type="text" class="form-control" id="verifyCode" name="verifyCode" autocomplete="off" />
					<div class="verifyCode">
						<img src="user.do?method=createVerify">
					</div>
					<div class="error">
						<c:if test="${not empty verifyCode_error }">${verifyCode_error }</c:if>
						<c:if test="${not empty noVC_error }">${noVC_error }</c:if>
					</div>
				</div>
				<button type="submit" id="regist" class="btn btn-primary">注册</button>
			</form>
		</div>
</body>
</html>