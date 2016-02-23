<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表格渐变色</title>
<style type="text/css">
 table {
	border: 1px red solid;	
	width: 500px;
	margin: auto;
}
	td {
	border: 1px blue solid;
	margin: 10px;
	padding:10px;
	text-align: center;
}
	th{
	background-color: maroon;
}
.one{
	background-color: blue;
}
.two{
	background-color: green;
}
.over{
	background-color: aqua;
}
</style>
<script type="text/javascript">
	onload = function(){
	var table = document.getElementsByTagName("table")[0];
	var rows = table.rows;
	for (var i = 0; i < rows.length; i++) {
		if(i%2==0){
			rows[i].className="one";
		}else{
			rows[i].className="two";
		}	
		var className;
		rows[i].onmouseover=function(){
			className = this.className;
			this.className="over";
		}
		rows[i].onmouseout=function(){
			this.className=className;
		}
	}
}
</script>
</head>
<body>
<br><br><br><br><br><br><br><br>
<center>
	<table>
  		<tr>
  			<th>姓名</th>
  			<th>年龄</th>
  			<th>地址</th>
  		</tr>
  		<tr>
  			<td>高杰</td>
  			<td>18</td>
  			<td>闵行</td>
  		</tr>
  		<tr >
  			<td>李刚</td>
  			<td>16</td>
  			<td>上海</td>
  		</tr>
  		<tr>
  			<td>赵士龙</td>
  			<td>22</td>
  			<td>东莞</td>
  		</tr>
  		<tr>
  			<td>鲁宾</td>
  			<td>29</td>
  			<td>北京</td>
  		</tr>
  		<tr>
  			<td>刘鹏</td>
  			<td>13</td>
  			<td>幼儿园</td>
  		</tr>
  		<tr>
  			<td>刘鹏</td>
  			<td>13</td>
  			<td>幼儿园</td>
  		</tr>
  		<tr>
  			<td>刘鹏</td>
  			<td>13</td>
  			<td>幼儿园</td>
  		</tr>
  		<tr>
  			<td>刘鹏</td>
  			<td>13</td>
  			<td>幼儿园</td>
  		</tr>
  		<tr>
  			<td>刘鹏</td>
  			<td>13</td>
  			<td>幼儿园</td>
  		</tr>
  		<tr>
  			<td>刘鹏</td>
  			<td>13</td>
  			<td>幼儿园</td>
  		</tr>
  		<tr>
  			<td>刘鹏</td>
  			<td>13</td>
  			<td>幼儿园</td>
  		</tr>
  	</table>
</center>	
</body>
</html>