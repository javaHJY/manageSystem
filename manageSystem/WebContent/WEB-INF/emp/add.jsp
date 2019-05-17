<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增员工</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<script type="text/javascript" src="js/jquery.js"></script>
<style type="text/css">
#main {
	width: 300px;
	margin: 50px auto;
}
#avatarShow{
	position:relative;
	left:120px;
	top:0px;
	width:50px;
	height:50px;
}
#avatarShow img{
	width:50px;
}
</style>
<script type="text/javascript">
	$().ready(function(){
		if(self==top){
			self.location="index";
		}
		$("#avatar").change(function(){
			var formData=new FormData();
			for(var i=0;i<$("[name=avatar]")[0].files.length;i++){
				formData.append("avatar",$("[name=avatar]")[0].files[i]);
			}
			$.ajax({
	            url:"EmployeeServlet?method=upload",
	            type:"post",
	            data:formData,
	            cache:false,
	            processData:false,
	            contentType:false,
	            resultType:"text",
	            success:function(data){
	                var ava="<img src='"+data+"'/>"+"<input type='hidden' name='img' value='"+data+"'/>";
	                $("#avatarShow").html(ava);
	            }
	        });
		});
		$(document).on("dblclick","#avatarShow img",function(){
			$(this).remove();
		});
	})
</script>
</head>
<body>
	<div id="main">
		<form class="form-horizontal" role="form" action="EmployeeServlet" method="post">
			<input type="hidden" name="method" value="add"/>
			<div class="form-group">
				<label class="col-sm-4 control-label">名字</label>
				<div class="col-sm-8">
					<input type="text" name="username" class="form-control" placeholder="请输入名字">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label">性别</label>
				<div class="col-sm-8">
					<label class="radio-inline"> 
						<input type="radio" name="sex" value="男" checked> 男
					</label> 
					<label class="radio-inline"> 
						<input type="radio" name="sex" value="女"> 女
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label">年龄</label>
				<div class="col-sm-8">
					<input type="text" name="age" class="form-control" placeholder="请输入年龄">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label">部门</label>
				<div class="col-sm-8">
					<select name="depId">
						<c:forEach items="${deps }" var="dep">
							<option  value ="${dep.id }">${dep.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label">头像</label>
				<div class="col-sm-8">
					<input id="avatar" type="file" name="avatar" class="form-control">
				</div>
			</div>
			<div id="avatarShow" class="form-group">
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<input type="submit" class="btn btn-primary" value="保存" />
				</div>
			</div>
		</form>
	</div>
</body>
</html>