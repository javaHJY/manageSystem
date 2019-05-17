<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>项目展示</title>
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
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		if(self==top){
			self.location="index";
		}
		$("#add").click(function() {
			window.location.href = "ProjectServlet?method=addView";
		});
		var id = -1;
		$("#update").click(function() {
			if (id != -1) {
				window.location.href = "ProjectServlet?method=updateView&id="+id;
			} else {
				alert("请选中一行数据");
			}
		});
		$("#delete").click(function() {
			if (id != -1) {
				window.location.href = "ProjectServlet?method=delete&id="+id;
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
	})
</script>
</head>
<body>
	<div id="main">
		<div id="title">
		<form class="form-horizontal" role="form" method="post" action="ProjectServlet">
				<input type="hidden" name="method" value="show" />
				<div id="findDiv">
					<div class="form-group">
						<div class="col-sm-4">
							<select name="proId" class="form-control">
								<option  value ="-1">请选择项目</option>
								<c:forEach items="${proList }" var="pro1">
									<option <c:if test="${pro1.id==pro.id }">selected="selected"</c:if> value ="${pro1.id }">${pro1.name }</option>
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
					<td>项目编号</td>
					<td>项目名</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pros }" var="pro1">
					<tr data-id="${pro1.id }">
						<td>${pro1.id }</td>
						<td>${pro1.name }</td>
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
			<li><a href="ProjectServlet?method=show&proId=${pro.id }&pageNow=1">首页</a></li>
			<li 
				<c:if test="${p.pageNow<=1 }">class="disabled"</c:if>>
				<a
					<c:if test="${p.pageNow>1 }">
						href="ProjectServlet?method=show&proId=${pro.id }&pageNow=${p.pageNow-1}" 
					</c:if>>上一页 
				</a>
			</li>
			<c:forEach varStatus="status" begin="${p.start }" end="${p.end }">
				<li><a
						<c:if test="${p.pageNow==status.index }">class="select"</c:if>
						href="ProjectServlet?method=show&proId=${pro.id }&pageNow=${status.index }"> ${status.index } 
					</a>
				</li>
			</c:forEach>
			<li <c:if test="${p.pageNow>=p.totalPage }">class="disabled"</c:if>>
				<a
					<c:if test="${p.pageNow<p.totalPage }">
						href="ProjectServlet?method=show&proId=${pro.id }&pageNow=${p.pageNow+1}"
					</c:if>>下一页
				</a>
			</li>
			<li><a href="ProjectServlet?method=show&proId=${pro.id }&pageNow=${p.totalPage}">尾页 </a></li>
		</ul>
	</div>
</body>
</html>