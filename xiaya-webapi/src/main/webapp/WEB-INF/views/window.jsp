<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function close(){
		window.history.back();
	}
	function _sina(){
		window.location.href="http://www.sina.com";
	}

	onload = function(){
		var input = document.getElementById("username");
		alert(input.value);
	}
	function _pwd(){
		var pwd = document.getElementById("password");
		alert(pwd.value);
	}
</script>
</head>
<body>
	<h1>bom和dom对象</h1>
	<div>
		<h2>history对象</h2>
		<input type="button" value="关闭当前网页" onclick="close()">
	</div>
	<div>
		<h2>location对象</h2>
		<button onclick="_sina()">访问新浪</button>
	</div>
	<div>
		<h2>dom常用方法</h2>
		用户名:<input type="text" id="username" name="username" value="123"><br>
		密码:<input type="password" id="password" name="password" onblur="_pwd()"><br>
		性别:<input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女">女
		
	</div>
</body>
</html>