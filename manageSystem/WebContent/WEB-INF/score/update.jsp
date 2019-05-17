<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>绩效页面</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery.js"></script>
<style type="text/css">
	#main{
		text-align: center;
		margin: 50px 0 0 20px;
	}
	#content .table{
		width:700px;
		margin-left: 200px;
	}
	#content .table tr th{
		text-align: center;
	}
	.pagination .select{
		color: #fff;
		background-color: #337ab7;
	}
</style>
<script type="text/javascript">
	$().ready(function(){
		if(self==top){
			self.location="index";
		}
		$(".table .value").change(function(){
			var scId=$(this).data("scid");
			var empId=$(this).data("empid");
			var proId=$(this).data("proid");
			var value=$(this).val();
			var obj=$(this);
			$.ajax({
				type: "post",
                url: "ScoreServlet?method=save",
                data: {scId:scId,empId:empId,proId:proId,value:value},
                dataType: "text",
              	success: function(data){
              		var arr=data.split(",");
              		obj.data("scid",arr[0]);
              		obj.parent().next().html(arr[1]);
                }
			});
		});
	})
</script>
</head>
<body>
	<div id="main">
		<div id="content">
			<form class="form-horizontal" role="form">
				<table class="table">
				  <thead>
				    <tr>
				      <th>员工编号</th>
				      <th>员工名</th>
				      <th>所属部门</th>
				      <th>参与项目</th>
				      <th>项目成绩</th>
				      <th>等级</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${sc }" var="score1">
				  		<tr>
					      <td>${score1.emp.id }</td>
					      <td>${score1.emp.name }</td>
					      <td>${score1.emp.dep.name }</td>
					      <td>${score1.pro.name }</td>
					      <td><input class="value" data-scid="${score1.id }" data-empid="${score1.emp.id }"
					      		data-proid="${score1.pro.id }" data-value="${score1.value }" 
					      		name="value" type="text" value="${score1.value }"/></td>
					      <td>${score1.grade }</td>
					    </tr>
				  	</c:forEach>
				  </tbody>
				</table>
			</form>
		</div>
		<ul class="pagination">
			<li><a href="ScoreServlet?method=updateView&pageNow=1">首页</a></li>
			<li 
				<c:if test="${p.pageNow<=1 }">class="disabled"</c:if>>
				<a
					<c:if test="${p.pageNow>1 }">
						href="ScoreServlet?method=updateView&pageNow=${p.pageNow-1}" 
					</c:if>>上一页 
				</a>
			</li>
			<c:forEach varStatus="status" begin="${p.start }" end="${p.end }">
				<li><a
						<c:if test="${p.pageNow==status.index }">class="select"</c:if>
						href="ScoreServlet?method=updateView&pageNow=${status.index }"> ${status.index } 
					</a>
				</li>
			</c:forEach>
			<li <c:if test="${p.pageNow>=p.totalPage }">class="disabled"</c:if>>
				<a
					<c:if test="${p.pageNow<p.totalPage }">
						href="ScoreServlet?method=updateView&pageNow=${p.pageNow+1}"
					</c:if>>下一页
				</a>
			</li>
			<li><a href="ScoreServlet?method=updateView&pageNow=${p.totalPage}">尾页 </a></li>
		</ul>
	</div>
</body>
</html>