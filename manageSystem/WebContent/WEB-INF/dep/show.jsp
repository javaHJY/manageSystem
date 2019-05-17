<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部门信息</title>
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
		$("#managePro").click(function() {
			if (id != -1) {
				window.location.href = "Dep2ProServlet?method=manageProView&depId="+id;
			}else{
				alert("请选中一行数据");
			}
		});
		$("#add").click(function() {
			window.location.href = "DepartmentServlet?method=addView";
		});
		var id = -1;
		$("#update").click(function() {
			if (id != -1) {
				window.location.href = "DepartmentServlet?method=updateView&id="+id;
			} else {
				alert("请选中一行数据");
			}
		});
		$("#delete").click(function() {
			if (id != -1) {
				window.location.href = "DepartmentServlet?method=delete&id="+id;
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
		<form class="form-horizontal" role="form" method="post" action="DepartmentServlet">
				<input type="hidden" name="method" value="show" />
				<div id="findDiv">
					<div class="form-group">
						<div class="col-sm-4">
							<select name="depName" class="form-control">
								<option  value ="-1">请选择部门</option>
								<c:forEach items="${depList }" var="dep1">
									<option <c:if test="${dep.name==dep1.name }">selected="selected"</c:if> value ="${dep1.name }">${dep1.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div  class="col-sm-5">
							<input type="text" name="empNums" class="form-control" placeholder="请输入人数" value="${dep.empNums }">
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
					<td>部门编号</td>
					<td>部门名称</td>
					<td>部门人数</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${deps }" var="dep1">
					<tr data-id="${dep1.id }">
						<td>${dep1.id }</td>
						<td>${dep1.name }</td>
						<td>${dep1.empNums }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<button id="managePro" type="button" class="btn btn-primary">管理项目</button>
			<button id="add" type="button" class="btn btn-primary">增加</button>
			<button id="update" type="button" class="btn btn-primary">修改</button>
			<button id="delete" type="button" class="btn btn-primary">删除</button>
		</div>
		<ul class="pagination">
			<li><a href="DepartmentServlet?method=show&depName=${dep.name }&pageNow=1">首页</a></li>
			<li 
				<c:if test="${p.pageNow<=1 }">class="disabled"</c:if>>
				<a
					<c:if test="${p.pageNow>1 }">
						href="DepartmentServlet?method=show&depName=${dep.name }&pageNow=${p.pageNow-1}" 
					</c:if>>上一页 
				</a>
			</li>
			<c:forEach varStatus="status" begin="${p.start }" end="${p.end }">
				<li><a
						<c:if test="${p.pageNow==status.index }">class="select"</c:if>
						href="DepartmentServlet?method=show&depName=${dep.name }&pageNow=${status.index }"> ${status.index } 
					</a>
				</li>
			</c:forEach>
			<li <c:if test="${p.pageNow>=p.totalPage }">class="disabled"</c:if>>
				<a
					<c:if test="${p.pageNow<p.totalPage }">
						href="DepartmentServlet?method=show&depName=${dep.name }&pageNow=${p.pageNow+1}"
					</c:if>>下一页
				</a>
			</li>
			<li><a href="DepartmentServlet?method=show&depName=${dep.name }&pageNow=${p.totalPage}">尾页 </a></li>
		</ul>
	</div>
</body>
</html>