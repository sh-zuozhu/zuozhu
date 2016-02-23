<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>向左向右</title>
<style type="text/css">
</style>
<script type="text/javascript">
	onload = function(){
		var first = document.getElementById("first");
		var second = document.getElementById("second");
		var options = first.options;
		var so = second.options;
		
		document.getElementById("add").onclick=function(){
			for (var i = 0; i < options.length; i++) {
				if(first.selectedIndex >= 0){
					second.appendChild(options[first.selectedIndex])
				}
			}
		}
		document.getElementById("remove").onclick=function(){
			for (var i = 0; i < so.length; i++) {
				if(second.selectedIndex >= 0){
					first.appendChild(so[second.selectedIndex])
				}
			}
		}
		document.getElementById("add_all").onclick=function(){
			for (var i = options.length-1; i >= 0; i--) {
				second.appendChild(options[i]);
			}
		}
		document.getElementById("remove_all").onclick=function(){
			for (var i = so.length-1; i >= 0; i--) {
				first.appendChild(so[i]);
			}
		}
		for (var i = 0; i < options.length; i++) {
			options[i].ondblclick=function(){
				second.appendChild(this);
				for (var i = 0; i < so.length; i++) {
					so[i].ondblclick=function(){
						first.appendChild(this);
					}
				}
			}
		}
	}
</script>
</head>
<body>
<br><br><br><br><br><br><br><br>
<center>
	<div style="border: 1px dashed #E6E6E6;width:350px; height: 200px; background-color: #E6E6E6;">
	<table width="285" height="169" border="0" align="left" cellpadding="0" cellspacing="0" style="margin: 15px 0px 0px 15px;">
		<tr>
			<td width="126">
				<select name="first" size="10" multiple="multiple" id="first" class="td3">
					<option value="选项1">选项1</option>
					<option value="选项2">选项2</option>
					<option value="选项3">选项3</option>
					<option value="选项4">选项4</option>
					<option value="选项5">选项5</option>
					<option value="选项6">选项6</option>
					<option value="选项7">选项7</option>
					<option value="选项8">选项8</option>
				</select>
			</td>
			<td width="69" valign="middle">
				<input name="add" id="add" type="button" calss="button" value="--->">
				<input name="add_all" id="add_all" type="button" calss="button" value="===>">
				<input name="remove" id = "remove" type="button" calss="button" value="&lt;--">
				<input name="remove_all" id="remove_all" type="button" calss="button" value="&lt;===">
			</td>
			<td width="127" align="left">
				<select name="second" size="10" multiple="multiple" class="td3" id="second">
					<option value="选项9">选项9</option>
				</select>
			</td>	
		</tr>
  	</table>
  	</div>
</center>	
</body>
</html>