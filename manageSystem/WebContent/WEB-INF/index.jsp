<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="css/index.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script>
	$().ready(function(){
		var websocket = null;
		
		//判断当前浏览器是否支持WebSocket
		if ('WebSocket' in window) {
			websocket = new WebSocket("ws://192.168.43.67:8080/manageWeb/websocket");
		} else {
			alert('没有建立websocket连接');
		}
		
		//连接发生错误的回调方法
		websocket.onerror = function() {
			//setMessage("错误");
		};
		
		//连接成功建立的回调方法
		websocket.onopen = function(event) {
			//	setMessage("建立连接");
		}
		
		//接收到消息的回调方法
		websocket.onmessage = function(event) {
			$("#title .online_session_count").html(event.data);
		}
		
		//连接关闭的回调方法
		websocket.onclose = function() {
			//setMessage("close");
		}
		
		//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口。
		window.onbeforeunload = function() {
			websocket.close();
		}
		$("#left .module .yi").click(function(){
			var id=$(this).data("ulid");
			$("#"+id).slideToggle(500);
		});
		$(".logout a").click(function(){
			$(document.body).append("<div class='mask'></div>");
			$(".alert").show();
			$(".cancel").click(function(){
				$(".mask").remove();
				$(".alert").hide();
				$(".alert .cancel").off("click");
			});
			$(".confirm").click(function(){
				location.href="user.do?method=logout";
			});
		});
	})
</script>
</head>
<body>
	<div id="container">
		<div id="top">
			<div id="title">员工管理系统
				<div class="online_session_count">
					在线人数:${online_session_count }
				</div>
			</div>
			<div class="logout">
				<a>注销</a>
			</div>
		</div>
		<div id="center">
	   		<div id="left">
	    		<div id="emp" class="module">
	    			<div class="yi" data-ulid="u1">员工模块</div>
	    			<ul id="u1">
	    				<li><a href="EmployeeServlet?method=show" target="content">展示员工</a></li>
	    				<li><a href="EmployeeServlet?method=addView" target="content">新增员工</a></li>
	    			</ul>
	    		</div>
	    		<div id="dep" class="module">
	    			<div class="yi" data-ulid="u2">部门模块</div>
	    			<ul id="u2">
	    				<li><a href="DepartmentServlet?method=show" target="content">展示部门</a></li>
	    				<li><a href="DepartmentServlet?method=addView" target="content">新增部门</a></li>
	    			</ul>
	    		</div>
	    		<div id="pro" class="module">
	    			<div class="yi" data-ulid="u3">项目模块</div>
	    			<ul id="u3">
	    				<li><a href="ProjectServlet?method=show" target="content">展示项目</a></li>
	    				<li><a href="ProjectServlet?method=addView" target="content">新增项目</a></li>
	    			</ul>
	    		</div>
	    		<div id="sc" class="module">
	    			<div class="yi" data-ulid="u4">绩效模块</div>
	    			<ul id="u4">
	    				<li><a href="ScoreServlet?method=show" target="content">展示绩效</a></li>
	    				<li><a href="ScoreServlet?method=updateView" target="content">修改绩效</a></li>
	    			</ul>
	    		</div>
	    	</div>
	    	<div id="right">
	   			<iframe class="content" name="content" src="EmployeeServlet?method=show"></iframe>
	   		</div>
	   	</div>
	   	<div id="bottom"></div>
	</div>
	<div class="alert">
		<div class="header">警告</div>
		<div class="body">你好，确认注销？</div>
		<div class="footer">
			<button class="confirm">确认</button>
			<button class="cancel">取消</button>
		</div>
	</div>
</body>
</html>