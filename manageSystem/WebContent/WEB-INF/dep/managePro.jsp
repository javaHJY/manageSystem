<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部门管理项目</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<script type="text/javascript" src="js/jquery.js"></script>
<style type="text/css">
	#main{
		width:600px;
		position: relative;
		left:80px;
		top:5px;
	}
	#main #title{
		text-align: center;
	}
	#have,#unhave{
		width:500px;
		height: 200px;
		border: 2px solid #337ab7;
		margin: 10px auto;
		border-radius: 2px
	}
	#have .have,#unhave .unhave{
		margin: 10px 0 0 10px;
	}
	#main #btns{
		text-align: center;
	}
	#have .selected,#unhave .selected{
		background-color: #d9534f;
	}
</style>
<script>
	$().ready(function(){
		$("#add").click(function(){
			var noProIds=new Array();
			$("#unhave .unhave").each(function(index,element){
				if($(this).hasClass("selected")){
					noProIds.push($(this).data("noproid"));
				}
			})
			if(noProIds.length!=0){
				window.location.href = "Dep2ProServlet?method=depAddPro&depId=${dep.id}&noProIds="+noProIds;
			}else {
				alert("请选中一行数据");
			}
		});
		$("#delete").click(function() {
			var proIds=new Array();
			$("#have .have").each(function(index,element){
				if($(this).hasClass("selected")){
					proIds.push($(this).data("proid"));
				}
			})
			if (proIds.length != 0) {
				window.location.href = "Dep2ProServlet?method=depDeletePro&depId=${dep.id}&proIds="+proIds;
			} else {
				alert("请选中一行数据");
			}
		});
		$("#have .have").click(function() {
			$(this).toggleClass('selected');
		});
		$("#have .have").dblclick(function() {
			var proId = $(this).data("proid");
			window.location.href = "Dep2ProServlet?method=depDeletePro&depId=${dep.id}&proId="+proId;
		});
		$("#unhave .unhave").click(function() {
			$(this).toggleClass('selected');
		});
		$("#unhave .unhave").dblclick(function() {
			var noProId = $(this).data("noproid");
			window.location.href = "Dep2ProServlet?method=depAddPro&depId=${dep.id}&noProId="+noProId;
		});
	})
</script>
</head>
<body>
	<div id="main">
		<div id="title">
			<h3>${dep.name }</h3>
		</div>
		<div id="have">
	        	<c:forEach items="${proList }" var="pro1">
	        		<button data-proid="${pro1.id }" class="have btn btn-primary">${pro1.name }</button>
		        </c:forEach>
		</div>
		<div id="btns">
			<button id="add" type="button" class="btn btn-primary">&uarr;</button>
			<button id="delete" type="button" class="btn btn-primary">&darr;</button>
		</div>
		<div id="unhave">
		        <c:forEach items="${proNoList }" var="proNo">
		        	<button data-noproid="${proNo.id }" class="unhave btn btn-primary">${proNo.name }</button>
		        </c:forEach>
		</div>
	</div>
</body>
</html>