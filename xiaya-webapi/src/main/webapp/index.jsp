<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理系统</title>
<jsp:include page="/commons/common-js.jsp" />
<style type="text/css">
.content {
	paddding: 10px 10px 10px 10px;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#menu').tree({
			//点击子节点触发的事件
			onClick : function(node) {
				if ($('#menu').tree("isLeaf", node.target)) {
					var tabs = $("#tabs");
					var tab = tabs.tabs("getTab", node.text);
					if (tab) {
						tabs.tabs("select", node.text);
					} else {
						tabs.tabs('add', {
							title : node.text,
							href : node.attributes.url,
							closable : true,
							bodyCls : "content"
						});
					}
				}
			}
		});
	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',title:'菜单',split:true"
		style="width: 180px;">
		<ul id="menu" class="easyui-tree"
			style="margin-top: 10px; margin-left: 5px;">
			<li><span>用户信息管理</span>
				<ul>
					<li data-options="attributes:{'url':''}">获取用户信息</li>
					<li data-options="attributes:{'url':''}">修改用户信息</li>
					<li data-options="attributes:{'url':''}">新增用户信息</li>
				</ul></li>
			<li><span>评价系统管理</span>
				<ul>
					<li data-options="attributes:{'url':''}">查询某个商品评价</li>
					<li data-options="attributes:{'url':''}">审核商品评价</li>
				</ul></li>
		</ul>
	</div>

	<div data-options="region:'center',title:''">
		<div id="tabs" class="easyui-tabs">
		
			<div title="首页" style="padding: 20px;"></div>
		</div>
	</div>
</body>
</html>