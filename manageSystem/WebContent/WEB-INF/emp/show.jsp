<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工信息</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<style type="text/css">
#main {
	width: 600px;
	margin: 20px auto;
	text-align: center;
}
#main #title{
	height:38px;
	position:relative;
}
#main form{
	position:relative;
	height: 38px;
}
#main #findDiv {
	position:absolute;
	left:5px;
	height:38px
	display: inline;
}
#main #findDiv div{
	float: left;
	width: 150px;
	height:34px
	display: inline;
}
#main #findDiv #subBtn{
	width: 60px;
}
.pagination .select {
	color: #fff;
	background-color: #337ab7;
}
#eTable .avatar{
	width: 28px;
	height:25px;
}
#bigAvatar{
	width:80px;
	height:80px;
	position:absolute;
	left:0;
	top:0;
	display: none;
}
#bigAvatar img{
	width:80px;
	height:80px;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		if(self==top){
			self.location="index";
		}
		$("#add").click(function() {
			window.location.href = "EmployeeServlet?method=addView";
		});
		var id = -1;
		$("#update").click(function() {
			if (id != -1) {
				window.location.href = "EmployeeServlet?method=updateView&id="+id;
			} else {
				alert("请选中一行数据");
			}
		});
		$("#delete").click(function() {
			if (id != -1) {
				window.location.href = "EmployeeServlet?method=delete&id="+id;
			} else {
				alert("请选中一行数据");
			}
		});
		$("#eTable tbody tr").click(function() {
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
				$(this).css("background-color","");
				id = -1;
			} else {
				$("#eTable tbody tr").removeClass('selected');
				$("#eTable tbody tr").css("background-color", "");
				$(this).addClass('selected');
				$(this).css("background-color","grey");
				id = $(this).data("id");
			}
		});
		$(".avatar").hover(function (event) {
			$("#bigAvatar").show();
			$("#bigAvatar img").attr("src",$(this).attr("src"));
			$("#bigAvatar").css({
				left:event.pageX-135,
				top:event.pageY-50
			});
		}, function () {
			$("#bigAvatar").hide();
	    });
	})
</script>
</head>
<body>
	<div id="main">
		<div id="title">
		<form class="form-horizontal" role="form" method="post" action="EmployeeServlet">
				<input type="hidden" name="method" value="show" />
				<div id="findDiv">
					<div class="form-group">
						<div  class="col-sm-5">
							<input type="text" name="username" class="form-control" placeholder="请输入姓名" value="${emp.name }">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2">
							<select name="sex" class="form-control">
								<option  value ="-1">请选择性别</option>
								<option <c:if test="${emp.sex=='男' }">selected="selected"</c:if> value ="男">男</option>
								<option <c:if test="${emp.sex=='女' }">selected="selected"</c:if> value ="女">女</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2">
							<input type="text" name="age" class="form-control" placeholder="请输入年龄" value="${emp.age }">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-4">
							<select name="depId" class="form-control">
								<option  value ="-1">请选择部门</option>
								<c:forEach items="${deps }" var="dep">
									<option <c:if test="${emp.dep.id==dep.id }">selected="selected"</c:if> value ="${dep.id }">${dep.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div id="subBtn">
						<input type="submit" class="btn btn-primary" />
					</div>
				</div>
			</form>
		</div>
		<table id="eTable" class="table table-striped table-bordered">
			<thead>
				<tr>
					<td>头像</td>
					<td>姓名</td>
					<td>性别</td>
					<td>年龄</td>
					<td>部门</td>
					<td>下载头像</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${emps }" var="emp">
					<tr data-id="${emp.id }">
						<td>
							<c:if test="${not empty emp.avatar&&!(emp.avatar eq 'null') }">
								<img class="avatar" src="${emp.avatar }"/>
							</c:if>
						</td>
						<td>${emp.name }</td>
						<td>${emp.sex }</td>
						<td>${emp.age }</td>
						<td>${emp.dep.name }</td>
						<td><a href="EmployeeServlet?method=downloadAvatar&id=${emp.id }" target="blank">下载</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<button id="add" type="button" class="btn btn-primary">增加</button>
			<button id="update" type="button" class="btn btn-primary">修改</button>
			<button id="delete" type="button" class="btn btn-primary">删除</button>
		</div>
		<ul class="pagination">
			<li><a href="EmployeeServlet?method=show&username=${emp.name }&sex=${emp.sex }&age=${emp.age }&depId=${emp.dep.id }&pageNow=1">首页</a></li>
			<li 
				<c:if test="${p.pageNow<=1 }">class="disabled"</c:if>>
				<a
					<c:if test="${p.pageNow>1 }">
						href="EmployeeServlet?method=show&username=${emp.name }&sex=${emp.sex }&age=${emp.age }&depId=${emp.dep.id }&pageNow=${p.pageNow-1}" 
					</c:if>>上一页 
				</a>
			</li>
			<c:forEach varStatus="status" begin="${p.start }" end="${p.end }">
				<li><a
						<c:if test="${p.pageNow==status.index }">class="select"</c:if>
						href="EmployeeServlet?method=show&username=${emp.name }&sex=${emp.sex }&age=${emp.age }&depId=${emp.dep.id }&pageNow=${status.index }"> ${status.index } 
					</a>
				</li>
			</c:forEach>
			<li <c:if test="${p.pageNow>=p.totalPage }">class="disabled"</c:if>>
				<a
					<c:if test="${p.pageNow<p.totalPage }">
						href="EmployeeServlet?method=show&username=${emp.name }&sex=${emp.sex }&age=${emp.age }&depId=${emp.dep.id }&pageNow=${p.pageNow+1}"
					</c:if>>下一页
				</a>
			</li>
			<li><a href="EmployeeServlet?method=show&username=${emp.name }&sex=${emp.sex }&age=${emp.age }&depId=${emp.dep.id }&pageNow=${p.totalPage}">尾页 </a></li>
		</ul>
	</div>
	<div id="bigAvatar">
		<img src=""/>
	</div>
</body>
</html>