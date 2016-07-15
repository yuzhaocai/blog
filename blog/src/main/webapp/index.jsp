<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath();     
	String basePath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;  
%>
<html>
<head>
<title>测试ajax操作</title>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
</script>
<script type="text/javascript" src="<%=basePath%>/static/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	function getUser(){
		$.ajax({
			type:"post",
			url:basePath + "/getUserById",
			dataType:"json",
			data:{"id":"2"},
			success:function(result){
				alert(result);
			},
			error:function(){
				alert("网络错误,请稍后重试！");
			}
		});
	}
</script>
</head>
<body>
	<input type="button" value="获取用户" onclick="getUser()">
</body>
</html>