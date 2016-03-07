<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
 		document.write("<p>This is inserted</p>");
 		onload = function(){
 			var div1 = document.createElement("div");
 	 		var node1 = document.createTextNode("新添加的文本");
 	 		div1.appendChild(node1);
			document.getElementById("addNode").onclick=function(){
 	 		document.getElementById("addNode").appendChild(div1);
			}
 		}
 		
</script>
</head>
<body>
	<div>
		<div>
			<a href="http://www.baidu.com">百度一下</a>
		</div><br>
		<div id="addNode">
			<a href="javascript:" id="addElement">添加元素</a>
		</div>
	</div>
</body>
</html>