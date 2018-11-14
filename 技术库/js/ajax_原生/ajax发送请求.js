
//兼容性创建xhr
function getXMLHttpRequest() {
	var xMLHttpRequest;
	if (window.ActiveXObject) {
		//是低版本IE
		xMLHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		//是低标准的
		xMLHttpRequest = new XMLHttpRequest();
	}
	return xMLHttpRequest;
}

var xMLHttpRequest = getXMLHttpRequest();
function checkUserName() {
	if (xMLHttpRequest) {
		//ajax开始了（四步走）
		/*get请求
		var url='/js_code/js_zendstudio/js_ajax_php/ajax/registerProcess.php?user_name='+($('user_name').value)+'&time='+new Date().getTime();
		xMLHttpRequest.open('get',url,true);
		xMLHttpRequest.onreadystatechange=chuli;
		xMLHttpRequest.send(null);*/

		//post请求
		var url = '/js_code/js_zendstudio/js_ajax_php/ajax/registerProcess.php';
		var data = 'user_name='+($('user_name').value) + '&time=' + new Date().getTime()

		xMLHttpRequest.open('post', url, true);
		xMLHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  //这句一定要放在这里
		xMLHttpRequest.onreadystatechange = chuli;
		xMLHttpRequest.send(data);
	}
}
function chuli() {
	if(xMLHttpRequest.readyState == 4) {
		window.alert('我是处理函数' + xMLHttpRequest.responseText + '&&' + xMLHttpRequest.responseXML);
	}
	
}


//定义一个id选择器
function $(id) {
	return document.getElementById(id);
}