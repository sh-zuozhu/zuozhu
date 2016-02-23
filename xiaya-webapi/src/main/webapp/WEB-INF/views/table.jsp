<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	onload = function(){
	document.getElementById("addUser").onclick = function(){
		var name = document.getElementsByName("username")[0].value;
		var email = document.getElementsByName("email")[0].value;
		var phone = document.getElementsByName("phone")[0].value;
		
		var nameTd = document.createElement("td");
		var nameTdValue = document.createTextNode(name);
		nameTd.appendChild(nameTdValue);
		var emailTd = document.createElement("td");
		var emailTdValue = document.createTextNode(email);
		emailTd.appendChild(emailTdValue);
		var phoneTd = document.createElement("td");
		var phoneTdValue = document.createTextNode(phone);
		phoneTd.appendChild(phoneTdValue);
		var aDelete = document.createElement("a");
		var deleteTdValue = document.createTextNode("delete");
		aDelete.appendChild(deleteTdValue);
		var deleteTd = document.createElement("td");
		deleteTd.appendChild(aDelete);
		
		aDelete.setAttribute("style", "cursor:pointer");
		aDelete.onclick = function(){
			if(confirm("确认要删除吗?")){
				this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);
			}
		}
		
		var tr = document.createElement("tr");
		tr.appendChild(nameTd);
		tr.appendChild(emailTd);
		tr.appendChild(phoneTd);
		tr.appendChild(deleteTd);
		
		var tbody = document.getElementsByTagName("tbody")[0];
		tbody.appendChild(tr);
	}
}
</script>
</head>
<body>
	<center>
	<br><br>
	添加用户:<br>
	<br>
	用户:<input type="text" name="username" >
	email:<input type="text" name="email">
	电话:<input type="text" name="phone"><br><br>
	<button id="addUser">提交</button>
	</center>
	<hr>
	<center>
		<table border="1" cellpadding="5" cellspacing="0">
			<tbody>
			<tr>
				<th>姓名</th>
				<th>email</th>
				<th>电话</th>
				<th>&nbsp;</th>
			</tr>
			<tr>
				<td>tom</td>
				<td>tom@tom.com</td>
				<td>8000</td>
				<td><a href="javascripte:void(0)">delete</a></td>
			</tr>
			<tr>
				<td>jerry</td>
				<td>jerry@jerry.com</td>
				<td>5000</td>
				<td><a href="javascripte:void(0)">delete</a></td>
			</tr>
			</tbody>
		</table>
	</center>
</body>
</html>