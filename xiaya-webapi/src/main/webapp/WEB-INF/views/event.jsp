<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function _onclick(){
		alert("为什么点击!");
	}
	function _move(){
		var div = document.getElementsByTagName("div")[0];
		div.style.backgroundColor = "red";
	}
	function _out(){
		var div = document.getElementsByTagName("div")[0];
		div.style.backgroundColor = "blue";
	}
	function _over(){
		var div = document.getElementsByTagName("div")[0];
		div.style.backgroundColor = "yellow";
	}
	onload = function(){
		var div = document.getElementsByTagName("div")[1];
		var body = document.body;
		div.onmouseover = function(){
			var div2 = document.createElement("div");
			div2.innerHTML = "我是动态创建的div!";
			
			body.appendChild(div2);
		}
		var a = document.createElement("a");
		a.setAttribute("href", "http://www.baidu.com");
		a.appendChild(document.createTextNode("超链接"));
		body.appendChild(a);
		
	}
	function _remove(){
		var one = document.getElementById("one");
		var body = one.parentNode;
		body.removeChild(one);
		body.removeChild(one.previousSibling)
	}
	function _replace(){
		var one = document.getElementById("one");
		var two = document.getElementById("two");
		one.parentNode.replaceChild(two, one);
	}
	function _clone(){
		var one = document.getElementById("one");
		var clone_three = document.getElementById("three").cloneNode(true);
		one.parentNode.replaceChild(clone_three, one);
	}
	function _allCheck() {
		var items = document.getElementsByName("items");
			for (var int = 0; int < items.length; int++) {
				items[int].checked=true;
			}
	}
	function _allUnCheck() {
		var items = document.getElementsByName("items");
			for (var int = 0; int < items.length; int++) {
				items[int].checked=false;
			}
	}
	function _revsernCheck() {
		var items = document.getElementsByName("items");
			for (var int = 0; int < items.length; int++) {
				if(items[int].checked){
					items[int].checked=false;
				}else{
					items[int].checked=true;
				}
			}
		//	items[int].checked=!items[int].checked;
	}
	function changeFontSize(value){
	var div = document.getElementById("news");
	div.className = value;
	}
	
</script>
<style type="text/css">
	.out{
		border:1px red solid;
		width:300px;
	}
	.max{
		font-size:32px;
		color:red;
	}
	.mid{
		font-size:22px;
		color:black;
	}
	.min{
		font-size:14px;
		color:blue;
	}
</style>
</head>
<body>
	<div onclick="_onclick()" onmousemove="_move()" onmouseout="_out()" onmouseover="_over()">
		div数据
	</div>
	<div>
		我是div
	</div>
	<div id="one">
		one
	</div>
	<div id="two">
		two
	</div>
	<div id="three">
		three
	</div>
	<div>
		<button onclick="_remove()" >删除one</button>
		<button onclick="_replace()" >被two替换</button>
		<button onclick="_clone()" >one被克隆的three替换</button>
	</div>
	<div>
		爱好:足球<input type="checkbox" name="items" value="足球">篮球<input type="checkbox" name="items" value="篮球">
		羽毛球<input type="checkbox" name="items" value="羽毛球">排球<input type="checkbox" name="items" value="排球">
		<button onclick="_allCheck()">全选</button>
		<button onclick="_allUnCheck()">全不选</button>
		<button onclick="_revsernCheck()">反选</button>
	</div>
	<div class="out">
	<div>
		<a href="javescript:void(0)" onclick="changeFontSize('max')">大</a>
		<a href="javescript:void(0)" onclick="changeFontSize('mid')">中</a>
		<a href="javescript:void(0)" onclick="changeFontSize('min')">小</a>
	</div>
	<div id="news" class="mid"> 
		2016年2月22日;
	</div>
	</div>
</body>
</html>