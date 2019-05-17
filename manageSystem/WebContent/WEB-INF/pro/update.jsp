<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>项目修改</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<style type="text/css">
#main {
	width: 300px;
	margin: 50px auto;
}
</style>
</head>
<body>
	<div id="main">
		<form class="form-horizontal" role="form" action="ProjectServlet" method="post">
			<input type="hidden" name="method" value="update"> 
			<input type="hidden" name="id" value=${pro.id } />
			<div class="form-group">
				<label class="col-sm-4 control-label">项目名称</label>
				<div class="col-sm-8">
					<input type="text" name="proName" class="form-control" value="${pro.name }">
				</div>
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