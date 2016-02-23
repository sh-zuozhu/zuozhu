<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二级联动</title>
<script type="text/javascript">
window.onload = function(){
 	/*
 	 * 当加载这个页面的时候，加载id为province的option
 	 */
	var provinceElement = document.getElementById("province");
	
	var xmlDoc = parseXML("/views/cities.xml");
	
	provinceElement.onchange = function(){
		/*
		 * 1、获取到选中的省的名字
		 * 2、遍历xmlDoc,查看哪个省的名字和获取到的省的名字是一样的
		 * 3、把一样的省下面的城市获取到，追加到city下
		 */
		var selectedOption = provinceElement.getElementsByTagName("option")[this.selectedIndex]
		var xmlProvinceElements = xmlDoc.getElementsByTagName("province");
		
		/*
		 * 把原来的城市清空
		 */
		var cityElement = document.getElementById("city");
		var city_options = cityElement.getElementsByTagName("option");
		var len = city_options.length;
		if(city_options.length>1){
			for(var i=1;i<len;i++){
				cityElement.removeChild(city_options[1]);
			}
		}
		for(var i=0;i<xmlProvinceElements.length;i++){//获取所有的省
			if(xmlProvinceElements[i].getAttribute("name")==selectedOption.value){//查看哪个省是选中的省
				var cityArray = xmlProvinceElements[i].getElementsByTagName("city");
				for(var j=0;j<cityArray.length;j++){//把选中的省的城市遍历
					var cityName = cityArray[j].firstChild.nodeValue;
					var optionElement = document.createElement("option");
					var textNode = document.createTextNode(cityName);
					optionElement.appendChild(textNode);
					document.getElementById("city").appendChild(optionElement);
				}
			}
		}
	}
	
	var xmlProvinceElements = xmlDoc.getElementsByTagName("province");
	for(var i=0;i<xmlProvinceElements.length;i++){
		//alert(xmlProvinceElements[i].name);  利用这个方法获取不到name的值
		/*********************************************************************/
			/*
			 * <option value="">${provinceName}</option>
			 */
			var provinceName = xmlProvinceElements[i].getAttribute("name");
			var optionElement = document.createElement("option");
			var textNode = document.createTextNode(provinceName);
			optionElement.appendChild(textNode);
			optionElement.setAttribute("value",provinceName);
		
			provinceElement.appendChild(optionElement);
	}
 }
 
/////////////////////////////////////////////////////////////////////////////////////////////	 
 /*
  * 用js脚本加载一个xml文件，把xml文件转化成document
  */
 function parseXML(filename){
      
	  var xmlDoc;
	   try { //Internet Explorer
			xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		} 
		catch (e) {
			try { //Firefox, Mozilla, Opera, etc.
				xmlDoc = document.implementation.createDocument("", "", null);
			} 
			catch (e) {
			}
		}
	    
		//关闭异步加载，这样确保在文档完全加载之前解析器不会继续脚本的执行。 
		xmlDoc.async=false;
		//知解析器加载名为 "note.xml" 的 XML 文档、
		xmlDoc.load(filename);
        
		return xmlDoc;	
}
</script>
</head>
<body>
	<select id="province" name="province">
		<option value="">请选择...</option>
	</select>
	<select id="city" name="city">
		<option value="">请选择...</option>
	</select>
</body>
</html>