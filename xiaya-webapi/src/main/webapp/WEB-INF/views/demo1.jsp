<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	ol,ul{
		color: #008000;
		font-style: oblique;
	}
	#_fond {
		font-size: large;
	}
	#_fond:HOVER {
		color: black;
	}
	.username{
		border-left-color: graytext;
	}
	div textarea {
		color: green;
		border-left-color: graytext;
		background-color: blue;
	}
	body{
            padding:10px;
            margin:10px;
            border: windowframe;
        }
	
</style>
<style type="text/css">
	@IMPORT url("/css/demo1.css");
</style>
<link rel="stylesheet" type="text/css" href="/css/demo2.css">
<script type="text/javascript">
// 	alert("hello!");
// 	confirm("继续?");
// 	var value = prompt("请输入数据:", "");
// 	document.write(value);
// 	alert(value);
// 	function method(count){
// 		return num;
// 	};
// 	var demo = function() {
// 		alert("匿名你好");
// 	}
// 	demo();
	window.onload = function() {
		var input = document.getElementById("username");
		alert(input);
		input.onclick = function(){
			this.value="";
		}
		input.onblur=function(){
			document.getElementById("username").innerHTML="<font color = 'red'>用户名不能为空</font>";
		}
		
		
	}
	//onload = function(){
	//	window.setInterval("setTime()", 1000);
	//}
	function setTime(){
		var data = new Date();
		document.getElementById("div3").innerHTML=data.toLocaleDateString()+data.toLocaleTimeString();
	}
	function demo2(){
		window.open("/page/window.html");
	}
	function showPic(whichPic){
// 		document.getElementById("placeholder").setAttribute("src", whichPic.getAttribute("t_href"));
		document.getElementById("placeholder").src=whichPic.getAttribute("href");
		var descripte = document.getElementById("descripte");
		var title = whichPic.getAttribute("title");
//		descripte.childNodes[0].nodeValue=title;
		descripte.firstChild.nodeValue=title;
	}
</script>

</head>
<body>
	<a name="top" style="color: aqua;font-style: italic; scrollbar-highlight-color: #008000">顶部</a>
	<font color="red" size="7" id="_fond">字体</font>
	<hr  size="1px" color="blue" >水平线
	
	<!-- a标签 两种用法-->
	<a href="http://www.baidu.com" />百度<br>
	<a href="#top"/>回到顶部<br>
	<a href="mailto://jiaqiang@b5m.com"/>联系我们<br>
	<a href="thunder://jiaqiang@b5m.com"/>美人鱼<br>
	<a href="javascript:void(0);" onclick="alert('真点击啊');">点击我</a><br>
	
	<ol>
		<li>coffee</li>
		<li>milk</li>
	</ol>
	<br>
	<ul>
		<li>coffee</li>
		<li>milk</li>
	</ul>
	<br>
	<dl>
		<dt>coffee</dt>
		<dd>black hot drink</dd>
	</dl>
	<br>
	<img alt="你好" src="http://q.qlogo.cn/qqapp/100243301/B2DA55DB66F90E022B21BAD0FA7C1FD1/100">
	
	<a  href="http://q.qlogo.cn/qqapp/100243301/B2DA55DB66F90E022B21BAD0FA7C1FD1/100" title="my image" onclick="showPic(this);return false;">firework</a>
	<br>
	<img id="placeholder" alt="my image gallery" src="">
	<p id="descripte">图片描述</p>
	<table border="1" width="70%" align="center">
		<tr>
			<td>1</td>
			<td>2</td>
			<td>3</td>
		</tr>
		<tr>
			<td>4</td>
			<td>5</td>
			<td>6</td>
		</tr>
	</table>
	<br>
	<form action="" method="get">
		用户名:<input type="text" name="username" class="username" id="username"><br>
		密码:<input type="password" name="password"><br>
		<input type="button" value="按钮"/><br>
		爱好 :<input type="checkbox" name="hobby" value="java">java
		<input type="checkbox" name="hobby" value="c#">c#
		<input type="checkbox" name="hobby" value="php">php<br>
		选择头像:<input type="file" name="file"><br>
		<input type="hidden">
		<input type="image"><br>
		性别:<input type="radio" name="sex" value="男"/>男
		<input type="radio" name="sex" value="女"/>女<br>
		<select name="country">
			<option>--请选择国籍--</option>
			<option value="中国">中国</option>
			<option value="美国">美国</option>
			<option value="日本">日本</option>
		</select><br>
		个人描述:<textarea rows="4" cols="30"></textarea><br>
		<input type="reset"><br>
		<input type="submit"><br>
	</form>
	<div>div
		<textarea rows="3" cols="20"></textarea>
	</div>
	<div>div2</div>
	<div id="div3">显示时间</div>
	<button onclick="demo2()">打开新的网页</button>
	<span>span</span>
	<span>span2</span>
	<p>p</p>
	<p>p2</p>
</body>

</html>